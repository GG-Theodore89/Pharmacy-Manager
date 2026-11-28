package com.pharmacie.ui;

import javafx.scene.control.TabPane;

public interface PageNavigator {

    void bindWorkspace(TabPane workspace);

    void open(Page page);
}
