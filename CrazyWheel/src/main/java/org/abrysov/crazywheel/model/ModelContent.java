package org.abrysov.crazywheel.model;

/**
 * Created by abrysov on 31.01.14.
 */
public class ModelContent {

    private String id;
    private String title;
    private String text;


    public ModelContent (String id, String title, String text) {
        this.setId(id);
        this.setText(text);
        this.setTitle(title);
    }

    public String getText() {

        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
