package devandroid.vinicanallesdev.equipesroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import devandroid.vinicanallesdev.equipesroom.R
import devandroid.vinicanallesdev.equipesroom.data.Equipe
import devandroid.vinicanallesdev.equipesroom.data.EquipeDatabase
import devandroid.vinicanallesdev.equipesroom.databinding.FragmentCadastroBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CadastroFragment : Fragment() {
    private var _binding: FragmentCadastroBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.cadastro_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_salvarEquipe -> {
                        val nome = binding.commonLayout.editTextNome.text.toString()
                        val estadio = binding.commonLayout.editTextEstadio.text.toString()
                        val anoFundacao = binding.commonLayout.editTextAnoDeFundacao.text.toString()

                        val e = Equipe(0, nome, estadio, anoFundacao)

                        val db = EquipeDatabase.getDatabase(requireActivity().applicationContext)

                        CoroutineScope(Dispatchers.IO).launch {
                            db.equipeDAO().inserirEquipe(e)
                        }

                        findNavController().popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}