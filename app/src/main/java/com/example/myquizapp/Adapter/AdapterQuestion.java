package com.example.myquizapp.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizapp.Activity.ManagerQuestionActivity;
import com.example.myquizapp.Activity.ManagerUserActivity;
import com.example.myquizapp.Activity.UpdateQuestionActivity;
import com.example.myquizapp.Activity.UpdateUserActivity;
import com.example.myquizapp.Model.Question;
import com.example.myquizapp.Model.User;
import com.example.myquizapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdapterQuestion extends RecyclerView.Adapter<AdapterQuestion.HolderQuestion> implements Filterable {
    ManagerQuestionActivity context;
    List<Question> questionList;
    List<Question> questionListOld;

    public AdapterQuestion(ManagerQuestionActivity context, List<Question> questionList) {
        this.context = context;
        this.questionList = questionList;
        this.questionListOld = questionList;
    }

    @NonNull
    @Override
    public HolderQuestion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question,parent,false);
        return new HolderQuestion(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderQuestion holder, int position) {
        Question question = questionList.get(position);
        holder.idtxt.setText(question.getQ_id());
        holder.nametxt.setText(question.getName_question());
        holder.senAtxt.setText("A. "+question.getSentence_a());
        holder.senBtxt.setText("B. "+question.getSentence_b());
        holder.senCtxt.setText("C. "+question.getSentence_c());
        holder.senDtxt.setText("D. "+question.getSentence_d());
        holder.answertxt.setText("Answer: "+question.getAnswer());
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.delete(question.getQ_id());
            }
        });
        holder.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateQuestionActivity.class);
                intent.putExtra("data", (Serializable) question);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if(search.isEmpty()){
                    questionList = questionListOld;
                }else{
                    List<Question> list = new ArrayList<>();
                    for(Question question : questionListOld){
                        if(question.getName_question().toLowerCase().contains(search.toLowerCase())){
                            list.add(question);
                        }
                    }
                    questionList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = questionList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                questionList = (List<Question>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class HolderQuestion extends RecyclerView.ViewHolder{
        TextView idtxt,nametxt,senAtxt,senBtxt,senCtxt,senDtxt,answertxt;
        ImageButton updatebtn,deletebtn;
        public HolderQuestion(@NonNull View itemView) {
            super(itemView);
            idtxt = itemView.findViewById(R.id.id);
            nametxt = itemView.findViewById(R.id.question);
            senAtxt = itemView.findViewById(R.id.sentence_a);
            senBtxt = itemView.findViewById(R.id.sentence_b);
            senCtxt = itemView.findViewById(R.id.sentence_c);
            senDtxt = itemView.findViewById(R.id.sentence_d);
            answertxt = itemView.findViewById(R.id.answer);
            updatebtn = itemView.findViewById(R.id.update);
            deletebtn = itemView.findViewById(R.id.delete);
        }
    }
}
