/*********************************************************************/
/*                                                                   */
/* Copyright 2012-2017 Rémi Synave, Anthony Desitter,                */
/*                     Nicolas Dubrunfaut, Maxime Langa,             */
/*                     Guillaume Langa                               */
/*                                                                   */
/* This file is part of MG2D.                                        */
/* This library uses javazoom library piece of code                  */
/* http://www.javazoom.net                                           */
/* and can be found with this library (file jlayer1.0.1.tar.gz)      */
/*                                                                   */
/* MG2D is free software: you can redistribute it and/or modify      */
/* it under the terms of the GNU General Public License as published */
/* by the Free Software Foundation, either version 3 of the License, */
/* or (at your option) any later version.                            */
/*                                                                   */
/* Foobar is distributed in the hope that it will be useful,         */
/* but WITHOUT ANY WARRANTY; without even the implied warranty of    */
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the      */
/* GNU General Public License for more details.                      */
/*                                                                   */
/* You should have received a copy of the GNU General Public License */
/* along with MG2D. If not, see <http://www.gnu.org/licenses/>.      */
/*                                                                   */
/*********************************************************************/

package mg2d.audio;

import java.io.FileInputStream;
import java.io.InputStream;

import mg2d.audio.player.Player;

/**
 * Cette classe permet la lecture d'un son, un bruitage ou une musique une fois. Pour jouer plusieurs fois le même son, il faudra instancier autant d'objets que de fois à lire le son.<br />
 * Cette classe sera réécrite d'ici peu. (avant 2025).
 */

public class BruitageATester extends Thread {

    private Player player;
    
    /**
     * Permet de créer et jouer une fois le son.<br />
     * Attention, ne fonctionne qu'avec des fichiers mp3.<br />
     * Pour lancer le son, il faut appeler la méthode lecture.
     * @param fic Le chemin vers le fichier de la musique.
     */
    public BruitageATester(String fic){
	try{  
	    FileInputStream fis = new FileInputStream(fic);
	    player = new Player(fis);
	}
	catch(Exception ex){}
	start();
    }

    /**
     * Permet de créer et jouer une fois le son.<br />
     * Attention, ne fonctionne qu'avec des fichiers mp3.<br />
     * Pour lancer le son, il faut appeler la méthode lecture.
     * @param is Le flux d'entrée vers le fichier de la musique.
     */
    public BruitageATester(InputStream is){
	try{  
	    player = new Player(is);
	}
	catch(Exception ex){}
	start();
    }

    /**
     * Arrête peut-être le son en cours.
     */
    public void arret(){
	player.close();
    }
	
    @Override
    public void run() {
	try{
	    player.play();
	}
	catch(Exception ex){}
	player.close();
	stop();
    }

	
}
