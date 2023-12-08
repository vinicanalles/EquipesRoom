package devandroid.vinicanallesdev.equipesroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import devandroid.vinicanallesdev.equipesroom.data.Equipe
import devandroid.vinicanallesdev.equipesroom.databinding.EquipeCelulaBinding

class EquipeAdapter(val equipesLista: ArrayList<Equipe>) :
    RecyclerView.Adapter<EquipeAdapter.EquipeViewHolder>(),
    Filterable {

    var listener: EquipeListener? = null

    var equipesListaFilterable = ArrayList<Equipe>()

    private lateinit var binding: EquipeCelulaBinding

    init {
        this.equipesListaFilterable = equipesLista
    }

    fun setClickListener(listener: EquipeListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipeViewHolder {
        binding = EquipeCelulaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EquipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EquipeViewHolder, position: Int) {
        holder.nomeVH.text = equipesListaFilterable[position].nome
        holder.estadioVH.text = equipesListaFilterable[position].estadio
    }

    override fun getItemCount(): Int {
        return equipesListaFilterable.size
    }

    inner class EquipeViewHolder(view: EquipeCelulaBinding) : RecyclerView.ViewHolder(view.root) {
        val nomeVH = view.nome
        val estadioVH = view.estadio

        init {
            view.root.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    interface EquipeListener {
        fun onItemClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                if (p0.toString().isEmpty())
                    equipesListaFilterable = equipesLista
                else {
                    val resultList = ArrayList<Equipe>()
                    for (row in equipesLista)
                        if (row.nome.lowercase().contains(p0.toString().lowercase()))
                            resultList.add(row)
                    equipesListaFilterable = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = equipesListaFilterable
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                equipesListaFilterable = p1?.values as ArrayList<Equipe>
                notifyDataSetChanged()
            }
        }
    }
}