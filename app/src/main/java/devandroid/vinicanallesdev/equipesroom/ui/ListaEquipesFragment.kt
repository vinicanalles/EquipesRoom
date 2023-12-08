package devandroid.vinicanallesdev.equipesroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import devandroid.vinicanallesdev.equipesroom.R
import devandroid.vinicanallesdev.equipesroom.adapter.EquipeAdapter
import devandroid.vinicanallesdev.equipesroom.data.Equipe
import devandroid.vinicanallesdev.equipesroom.data.EquipeDatabase
import devandroid.vinicanallesdev.equipesroom.databinding.FragmentListaEquipesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaEquipesFragment : Fragment() {

    private var _binding: FragmentListaEquipesBinding? = null

    private val binding get() = _binding!!

    lateinit var equipeAdapter: EquipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaEquipesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fab.setOnClickListener { findNavController().navigate(R.id.action_listaEquipesFragment_to_cadastroFragment) }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)

                val searchView = menu.findItem(R.id.action_search).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        equipeAdapter.filter.filter(p0)
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {

        val db = EquipeDatabase.getDatabase(requireActivity().applicationContext)
        var equipesLista : ArrayList<Equipe>

        val recyclerView = binding.recyclerview

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            equipesLista = db.equipeDAO().listarEquipes() as ArrayList<Equipe>
            equipeAdapter = EquipeAdapter(equipesLista)

            withContext(Dispatchers.Main) {
                recyclerView.adapter = equipeAdapter

                val listener = object : EquipeAdapter.EquipeListener {
                    override fun onItemClick(pos: Int) {
                        val e = equipeAdapter.equipesListaFilterable[pos]

                        val bundle = Bundle()
                        bundle.putSerializable("equipe", e)

                        findNavController().navigate(
                            R.id.action_listaEquipesFragment_to_detalheFragment,
                            bundle
                        )
                    }
                }
                equipeAdapter.setClickListener(listener)
            }
        }
    }
}