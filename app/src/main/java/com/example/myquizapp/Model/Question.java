package com.example.myquizapp.Model;

import java.io.Serializable;

public class Question implements Serializable {
    private String q_id, name_question, category;
    private String sentence_a, sentence_b, sentence_c, sentence_d, answer;

    public Question(String q_id, String name_question, String sentence_a, String sentence_b, String sentence_c, String sentence_d, String answer) {
        this.q_id = q_id;
        this.name_question = name_question;
        this.sentence_a = sentence_a;
        this.sentence_b = sentence_b;
        this.sentence_c = sentence_c;
        this.sentence_d = sentence_d;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQ_id() {
        return q_id;
    }

    public void setQ_id(String q_id) {
        this.q_id = q_id;
    }

    public String getName_question() {
        return name_question;
    }

    public void setName_question(String name_question) {
        this.name_question = name_question;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSentence_a() {
        return sentence_a;
    }

    public void setSentence_a(String sentence_a) {
        this.sentence_a = sentence_a;
    }

    public String getSentence_b() {
        return sentence_b;
    }

    public void setSentence_b(String sentence_b) {
        this.sentence_b = sentence_b;
    }

    public String getSentence_c() {
        return sentence_c;
    }

    public void setSentence_c(String sentence_c) {
        this.sentence_c = sentence_c;
    }

    public String getSentence_d() {
        return sentence_d;
    }

    public void setSentence_d(String sentence_d) {
        this.sentence_d = sentence_d;
    }
}
