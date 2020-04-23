package com.imdb.imdb_api.ui.films.filmDetail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apka6.AdapterDetail
import com.example.apka6.AdapterDetailDirector

import com.imdb.imdb_api.R
import com.imdb.imdb_api.ui.films.FilmsViewModel
import kotlinx.android.synthetic.main.fragment_detail_film.*
import java.net.URL

/**
 * A simple [Fragment] subclass.
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION")
class DetailFilmFragment : Fragment() {
    private lateinit var filmsViewModel: FilmsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        filmsViewModel =
        ViewModelProviders.of(this).get(FilmsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_detail_film, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        filmsViewModel = activity!!.run{ViewModelProviders.of(this).get(filmsViewModel::class.java)}


        val recyclerActorsView = view!!.findViewById<RecyclerView>(R.id.rv_details_actors)
        val recyclerDirectorView = view!!.findViewById<RecyclerView>(R.id.rv_details_director)

        val adapterActor = AdapterDetail(requireContext(), ::viewActorClassToDataBase, ::checkifActorExistsInDB)
        val adapterDirector = AdapterDetailDirector(requireContext(), ::viewDirectorClassToDataBase, ::checkifDirectorExistsInDB)


        filmsViewModel.batmanMutableActorList.observe(this, Observer { adapterActor.setData(it.Actors)
           zadanie2.text=it.Released
        })
        filmsViewModel.batmanMutableDirectorList.observe(this, Observer { adapterDirector.setData(it.Director)})


        filmsViewModel.filmClass.observe(this, Observer {
            if(it.filmPoster!="N/A") {
            val url = URL(it.filmPoster)
//            Glide.with(requireContext())
//                .load(url)
//                .into(imageDetailMovie)
        }

        })




        filmsViewModel.filmClass.observe(this, Observer {
            titleImage.text = it.filmTitle
        })

        recyclerActorsView.adapter = adapterActor
        recyclerDirectorView.adapter = adapterDirector

        recyclerActorsView.layoutManager = LinearLayoutManager(context)
        recyclerDirectorView.layoutManager = LinearLayoutManager(context)

    }

    private fun viewActorClassToDataBase(c : DetailFilmActorsClass){
        filmsViewModel.checkActorsFromDetail(c, requireContext())
    }
    private fun viewDirectorClassToDataBase(c : DetailFilmDirectorClass){
        filmsViewModel.checkDirectorsFromDetail(c, requireContext())
    }
    private fun checkifActorExistsInDB(c:DetailFilmActorsClass):Int{
        return filmsViewModel.checkifActorExistsInDB(c,requireContext())
    }
    private fun checkifDirectorExistsInDB(c : DetailFilmDirectorClass):Int{
        return filmsViewModel.checkifDirectorExistsInDB(c,requireContext())
    }

    private fun editLike(id: Int, title: String, year: String){
        filmsViewModel.editLike(id, requireContext(), title, year)
    }

}
