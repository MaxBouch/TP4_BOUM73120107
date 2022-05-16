package org.calma.TP4.views;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class MenuView {
    private MenuBar view;

    private Menu menuOption;
    private Menu menuAide;

    private CheckMenuItem modePro;
    private Menu menuAPropos;

    public MenuView() {
        this.view = new MenuBar();

        this.menuOption = new Menu("Options");
        this.menuAide = new Menu("Aide");

        this.modePro = new CheckMenuItem("Mode Pro");

        this.menuAPropos = new Menu("À propos de...");

        this.menuOption.getItems().add(modePro);

        this.menuAide.getItems().add(menuAPropos);

        this.view.getMenus().addAll(menuOption, menuAide);
    }

    public MenuBar getView() {
        return view;
    }

    public void setView(MenuBar view) {
        this.view = view;
    }

    public Menu getMenuOption() {
        return menuOption;
    }

    public void setMenuOption(Menu menuOption) {
        this.menuOption = menuOption;
    }

    public Menu getMenuAide() {
        return menuAide;
    }

    public void setMenuAide(Menu menuAide) {
        this.menuAide = menuAide;
    }

    public CheckMenuItem getModePro() {
        return modePro;
    }

    public void setModePro(CheckMenuItem modePro) {
        this.modePro = modePro;
    }

    public Menu getMenuAPropos() {
        return menuAPropos;
    }

    public void setMenuAPropos(Menu menuAPropos) {
        this.menuAPropos = menuAPropos;
    }
}
