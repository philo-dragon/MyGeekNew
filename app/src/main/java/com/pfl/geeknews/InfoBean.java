package com.pfl.geeknews;

import org.jetbrains.annotations.NotNull;

public class InfoBean {
    @NotNull
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_ITEM = 2;
    @NotNull
    public String title;
    @NotNull
    public String content;
    @NotNull
    public int type;

    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    @NotNull
    public String getContent() {
        return content;
    }

    public void setContent(@NotNull String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
