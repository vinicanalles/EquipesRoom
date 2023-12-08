package devandroid.vinicanallesdev.equipesroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import devandroid.vinicanallesdev.equipesroom.R
import devandroid.vinicanallesdev.equipesroom.data.Equipe
import devandroid.vinicanallesdev.equipesroom.data.EquipeDatabase
import devandroid.vinicanallesdev.equipesroom.databinding.FragmentDetalheBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetalheFragment: Fragment() {

    private var _binding: FragmentDetalheBinding? = null

    private val binding get() = _binding!!

    lateinit var equipe: Equipe

    lateinit var nome: EditText
    lateinit var estadio: EditText
    lateinit var anoFundacao: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetalheBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        equipe = requireArguments().getSerializable("equipe", Equipe::class.java) as Equipe

        nome = binding.commonLayout.editTextNome
        estadio = binding.commonLayout.editTextEstadio
        anoFundacao = binding.commonLayout.editTextAnoDeFundacao

        nome.setText(equipe.nome)
        estadio.setText(equipe.estadio)
        anoFundacao.setText(equipe.anoDeFundacao)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.detalhe_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_alterarEquipe -> {
                        val db = EquipeDatabase.getDatabase(requireActivity().applicationContext)

                        val equipeUpdate = Equipe(equipe.id, nome.text.toString(), estadio.text.toString(), anoFundacao.text.toString())

                        CoroutineScope(Dispatchers.IO).launch {
                            db.equipeDAO().atualizarEquipe(equipeUpdate)
                        }

                        findNavController().popBackStack()
                        true
                    }
                    R.id.action_excluirEquipe -> {
                        val db = EquipeDatabase.getDatabase(requireActivity().applicationContext)

                        CoroutineScope(Dispatchers.IO).launch {
                            db.equipeDAO().apagarEquipe(equipe)
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