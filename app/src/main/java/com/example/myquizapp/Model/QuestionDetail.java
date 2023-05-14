package com.example.myquizapp.Model;

public class QuestionDetail {
    private String q_id, name_question;
    private String sentence_a, sentence_b, sentence_c, sentence_d, answer;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
