package io.github.filipebezerra.ebooksfordevelopers.model;

public interface NavDrawerItem {

    int getId();
    String getLabel();
    int getType();
    boolean isEnabled();
    boolean updateActionBarSubtitle();

}
