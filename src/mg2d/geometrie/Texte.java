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
/* MG2D is distributed in the hope that it will be useful,           */
/* but WITHOUT ANY WARRANTY; without even the implied warranty of    */
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the      */
/* GNU General Public License for more details.                      */
/*                                                                   */
/* You should have received a copy of the GNU General Public License */
/* along with MG2D. If not, see <http://www.gnu.org/licenses/>.      */
/*                                                                   */
/*********************************************************************/

package mg2d.geometrie;


import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Cette classe permet la création de textes.<br />
 * Un Texte est définie par une chaîne de caractères, d'une police de texte et d'un Point.
 * @author Equipe 2D, Rémi Synave
 * @version 2.8
 */
public class Texte extends Dessin {

    // Attributs //

    //constantes utiilisée pour centrer le texte
    public static final int CENTRE=0;
    public static final int GAUCHE=1;

    private String texte;
    private Font police;

    private Point a;	// Il s'agit du point central du texte /!\ //

    private int largeur, hauteur;

    private int typeAlign;

    // Constructeur //

    /**
     * Construit le texte noir "Ceci est un texte" centré en (100,100).
     */
    public Texte(){
	super();
	texte = new String("Ceci est un texte");
	police = new Font("Calibri", Font.TYPE1_FONT, 10);
	a = new Point(100,100);

	Graphics g = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).getGraphics();
	FontMetrics metr = g.getFontMetrics ( police );

	largeur = metr.stringWidth ( texte );
	hauteur = police.getSize();
	typeAlign = CENTRE;
    }

    /**
     * Construit un texte sur le modèle d'un texte.
     * @param t Le texte à copier.
     */
    public Texte (Texte t){
	super(t.getCouleur());
	texte=new String(t.texte);
	police = new Font(t.police.getFontName(), t.police.getStyle(), t.police.getSize());
	a = new Point(t.a);
	Graphics g = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).getGraphics();
	FontMetrics metr = g.getFontMetrics ( police );

	largeur = metr.stringWidth ( texte );
	hauteur = police.getSize();
	typeAlign=t.typeAlign;
    }

    // Couleur par défaut //

    /**
     * Construit un texte centré à partir d'une chaîne de caractères, d'une police et d'un Point.<br />
     * Il s'agit du constructeur sans couleur, ainsi l'objet sera noir.<br />
     * /!\ Attention ! Le Point correspondant au centre du Texte.
     * @param texte Chaîne de caractères à afficher.
     * @param police Police de texte.
     * @param a Point central du texte.
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/Font.html" target="_blank">Font</a>
     * @see Point
     */
    public Texte ( String texte, Font police, Point a ) {

	super ( Couleur.NOIR );

	this.texte = new String(texte);
	this.police = police;
	this.a = new Point ( a );
	Graphics g = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).getGraphics();
	FontMetrics metr = g.getFontMetrics ( police );

	largeur = metr.stringWidth ( texte );
	hauteur = police.getSize();
	typeAlign = CENTRE;
    }

    /**
     * Construit un texte à partir d'une chaîne de caractères, d'une police et d'un Point et d'un type d'alignement.<br />
     * Il s'agit du constructeur sans couleur, ainsi l'objet sera noir.<br />
     * /!\ Attention ! Le Point correspondant au centre du Texte.
     * @param texte Chaîne de caractères à afficher.
     * @param police Police de texte.
     * @param a Point central du texte.
     * @param typeAlign type d'alignement (gauche ou centre).
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/Font.html" target="_blank">Font</a>
     * @see Point
     */
    public Texte ( String texte, Font police, Point a, int typeAlign ) {

	super ( Couleur.NOIR );

	this.texte = new String(texte);
	this.police = police;
	this.a = new Point ( a );
	Graphics g = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).getGraphics();
	FontMetrics metr = g.getFontMetrics ( police );

	largeur = metr.stringWidth ( texte );
	hauteur = police.getSize();
	this.typeAlign = typeAlign;
    }

    // Couleur définie par l'utilisateur //

    /**
     * Construit un Texte centré à partir d'une chaîne de caractères, d'une police et d'un Point.
     * @param couleur Couleur du Texte
     * @param texte Chaîne de caractères à afficher.
     * @param police Police de texte.
     * @param a Point central du texte.
     * @see Couleur
     * @see Point
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/Font.html" target="_blank">Font</a>
     */
    public Texte ( Couleur couleur, String texte, Font police, Point a ) {

	super ( couleur );

	this.texte = new String(texte);
	this.police = police;
	this.a = new Point ( a );
	Graphics g = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).getGraphics();
	FontMetrics metr = g.getFontMetrics ( police );

	largeur = metr.stringWidth ( texte );
	hauteur = police.getSize();
	typeAlign = CENTRE;
    }

    /**
     * Construit un Texte à partir d'une chaîne de caractères, d'une police et d'un Point et d'un type d'alignement.
     * @param couleur Couleur du Texte
     * @param texte Chaîne de caractères à afficher.
     * @param police Police de texte.
     * @param a Point central du texte.
     * @param typeAlign type d'alignement (gauche ou centre).
     * @see Couleur
     * @see Point
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/Font.html" target="_blank">Font</a>
     */
    public Texte ( Couleur couleur, String texte, Font police, Point a, int typeAlign ) {

	super ( couleur );

	this.texte = new String(texte);
	this.police = police;
	this.a = new Point ( a );

	Graphics g = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).getGraphics();
	FontMetrics metr = g.getFontMetrics ( police );

	largeur = metr.stringWidth ( texte );
	hauteur = police.getSize();
	this.typeAlign = typeAlign;
    }

    // Accesseurs //

    // Getter //

    /**
     * Retourne la chaîne de caractères à afficher.
     * @return Le texte à afficher.
     */
    public String getTexte () {

	return new String(texte);
    }

    /**
     * Retourne la police de texte.
     * @return Police de caractères utilisée.
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/Font.html" target="_blank">Font</a>
     */
    public Font getPolice () {

	return police;
    }

    /**
     * Retourne le point central.
     * @return a Point central du Texte.
     * @see Point
     */
    public Point getA () {

	return new Point(a);
    }

    /**
     * Retourne la largeur du texte.
     * @return Largeur du texte.
     */
    public int getLargeur () {
	return largeur;
    }

    /**
     * Retourne la largeur du texte.
     * @return Largeur du texte.
     */
    public int getHauteur () {
	return hauteur;
    }

    /**
     * Implémentation de la méthode getBoiteEnglobante() de la classe abstraite Dessin.<br />
     * Elle retourne une BoiteEnglobante entourant le texte.
     * @return BoiteEnglobante BoiteEnglobante entourant le texte.
     */
    public BoiteEnglobante getBoiteEnglobante() {
	if(typeAlign==CENTRE){
	    Point bg = new Point(a.getX()-largeur/2,a.getY()-(hauteur/2));
	    Point hd = new Point(a.getX()+largeur/2,a.getY()+(hauteur/2));
	    
	    return new BoiteEnglobante(bg,hd);
	}
	
	Point bg = new Point(a.getX(),a.getY()-(hauteur/2));
	Point hd = new Point(a.getX()+largeur,a.getY()+(hauteur/2));
	return new BoiteEnglobante(bg,hd);
	
    }

    /**
     * Implémentation de la méthode translater() de la classe abstraite Dessin.<br />
     * @param dx pas de translation suivant X
     * @param dy pas de translation suivant Y
     */
    public void translater ( int dx, int dy ){
	a.translater(dx,dy);
    }

    // Setter //

    /**
     * Permet d'attribuer une nouvelle chaîne de caractères à afficher.
     * @param texte Chaîne de caractères
     */
    public void setTexte ( String texte ) {

	this.texte = new String(texte);
    }

    /**
     * Permet d'attribuer une nouvelle police au texte.
     * @param police Nouvelle police.
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/Font.html" target="_blank">Font</a>
     */
    public void setPolice ( Font police ) {

	this.police = police;
    }

    /**
     * Permet d'attribuer un nouveau point d'affichage.
     * @param a Nouveau point central du texte.
     * @see Point
     */
    public void setA ( Point a ) {

	this.a = new Point(a);
    }

    // Méthodes //

    /**
     * Implémentation de la méthode afficher() de la classe abstraite Dessin.<br />
     * Elle permet d'afficher un Texte sur la zone d'affichage.
     * <br /><br />
     * On récupère d'abord la couleur et la police. Une fois la police récupérée, on crée un FontMetrics afin d'avoir la dimension en largeur du texte ce qui nous permets d'afficher correctement le Texte avec le Point central donné par l'utilisateur.
     * @param g Graphics.
     */
    public void afficher ( Graphics g ) {
	
	g.setColor ( getCouleur() );
	g.setFont ( police );
	
	FontMetrics metr = g.getFontMetrics ( police );
       
	int ox=0;
	//Pourquoi ? je ne sais plus...
	int oy = (int)g.getClipBounds().getHeight()-a.getY()+(metr.getHeight()/4) ;;
	
	if(typeAlign==GAUCHE)
	    ox = a.getX();
	else
	    ox = a.getX() - ( metr.stringWidth ( texte ) / 2 );
	
	g.drawString ( texte, ox, oy );
    }

    /**
     * Méthode toString retournant un description du texte.<br />
     * La chaine de caractères retournée est de la forme "Message m centrée en (x,y)".
     * @return Une chaîne de caractères décrivant le texte.
     */
    public String toString(){
	if(typeAlign==CENTRE)
	    return new String("Message \""+texte+"\" centré en "+getA());
	else
	    return new String("Message \""+texte+"\" commencant en "+getA());
    }

    /**
     * Méthode equals permettant de tester l'égalité entre un texte et un objet passé en paramètre.
     * @return Vrai si l'objet passé en paramètre est un texte dont les caractéristiques sont les mêmes que le texte sur lequel la méthode est appelée.
     */
    public boolean equals(Object obj){
	if (obj==this) {
            return true;
        }

        // Vérification du type du paramètre
        if (obj instanceof Texte) {
            // Vérification des valeurs des attributs
	    Texte other = (Texte) obj;
	    return super.equals(other) && a.equals(other.a) && texte.equals(other.texte) && police.equals(other.police);
	}
	return false;
    }
}
