package com.squalala.hardgamerdz.eventbus;

import com.squalala.hardgamerdz.greendao.Article;

/**
 * Created by Back Packer
 * Date : 04/10/15
 */
public class PostEvent {

    private Article article;

    public PostEvent(Article article) {
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }
}

