package com.example.flowerparty;

import java.io.Serializable;

public class Journal implements Serializable {


    int seq;
    String title;
    String contents;
    int isdone;

    public Journal(int seq, String title, String contents, int isdone) {
        this.seq = seq;
        this.title = title;
        this.contents = contents;
        this.isdone = isdone;
    }


    public Journal(String title, String contents, int isdone) {
        this.title = title;
        this.contents = contents;
        this.isdone = isdone;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getContents() {
        return contents;
    }

    public void setContents() {
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle() {
        this.title = title;
    }

    public int getIsdone() {
        return isdone;
    }

    public void setIsdone() {
        this.isdone = isdone;
    }
}
