package space.dorzhu.store

import Adapters.CatalogGridAdapter
import Parsing.Parsing
import Some_objects.Product
import Some_objects.doAsync
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import space.dorzhu.store.Shop

class ProductDetail : AppCompatActivity() {
    private val LOG_TAG: String = "TAG"
    var catalogGridAdapter: CatalogGridAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        catalogGridAdapter = CatalogGridAdapter(this, ArrayList<Product>())

        btnReturn.setOnClickListener { finish() }

        val link2Detail = intent.getStringExtra("link2Detail")

        doAsync {
            val parser = Parsing()
            val product = parser.parseDetail(link2Detail!!)
            this.runOnUiThread{

                Picasso.get().load(product.img).fit().centerInside()
                    .placeholder(R.drawable.product_placeholder).into(productImage)
                tvProductName.text = product.name
                tvProductPrice.text = product.price
                tvDescription.text = product.description

                btnAdd2Basket.setOnCheckedChangeListener { buttonView, isChecked ->
                    if(isChecked) {
                        catalogGridAdapter!!.add2Cart(product)
                    } else{
                       catalogGridAdapter!!.removeFromCart(product)
                    }
                }

                disableProgressBar()
            }
        }.execute()

    }

    private fun disableProgressBar() {
        main_wrapper.removeView(bar_wrapper)
    }
}