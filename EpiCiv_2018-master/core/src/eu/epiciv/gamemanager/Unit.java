package eu.epiciv.gamemanager;

import com.badlogic.gdx.graphics.Texture;

/*********
 SETTING UNITS
 ********/
public class Unit {
    public int x = 0;
    public int y = 0;
    public int pv = 0;
    public int mv = 0;
    public int mvt = 0;
    public int dgt = 0;
    public int win = 0;
    public int recruit = -10;
    public String type = "???";
    public Texture img;

    public void spearman(int nx, int ny){
        x = nx;
        y = ny;
        pv = 20;
        mv = 2;
        mvt = 0;
        dgt = 6;
        win = 6;
        recruit = -4;
        type = "spearman";
        img = new Texture("units/" + type + ".png");
    }

    public void swordsman(int nx, int ny){
        x = nx;
        y = ny;
        pv = 20;
        mv = 2;
        mvt = 0;
        dgt = 4;
        win = 4;
        recruit = -2;
        type = "swordsman";
        img = new Texture("units/" + type + ".png");
    }

    public void horseman(int nx, int ny){
        x = nx;
        y = ny;
        pv = 20;
        mv = 4;
        mvt = 0;
        dgt = 9;
        win = 9;
        recruit = -7;
        type = "horseman";
        img = new Texture("units/" + type + ".png");
    }
    public void castle(int nx, int ny){
        x = nx;
        y = ny;
        pv = 50;
        mv = 0;
        mvt = 0;
        dgt = 10;
        win = 5;
        recruit = 0;
        type = "castle";
        img = new Texture("units/" + type + ".png");
    }
    public void builder(int nx, int ny){
        x = nx;
        y = ny;
        pv = 1;
        mv = 2;
        mvt = 0;
        dgt = 0;
        win = 0;
        recruit = -10;
        type = "builder";
        img = new Texture("units/" + type + ".png");
    }
}