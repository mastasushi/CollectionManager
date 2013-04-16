package com.svar.collectionManager;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainCollectionManager {

	static Logger log = LoggerFactory.getLogger(MainCollectionManager.class);

	private static final String mediaDir = System.getProperty("mediaDir",
			"media");
	private static final String outputDir = System.getProperty("outputDir");

	private String[] listCategories = new String[] { "Cosplay", "Animated",
			"Fakes", "vlcsnap" };

	private String[] listActresses = new String[] { "Maria Ozawa", "Shinozaki",
			"Saori Hara", "Sharapova", "Rihanna", "Marjolaine" };

	private String[] listArtists = new String[] { "Xteal", "Saigado",
			"Azasuke Wind", "AwesomeArtist", "Radprofile", "Katsura", "Humio",
			"MugenSaku", "Eroquis", "Ernie Centofanti" };

	private String[] listSeries = new String[] {
	/* # Themes # */
	"Crossover", "Nintendo", "Ghibli",

	/* Comics */
	"Avengers", "Spiderman", "Teen Titans", "Marvel", "DC", "X-Men",
			"Generation X", "Comics",

			/* # Series # */
			/* Famous */
			"Naruto", "One Piece", "Bleach", "Saint Seiya", "Ranma",
			"Sailor Moon", "Pokemon",

			/* Avatar the last airbender */
			"Legend of Korra", "Avatar the last Airbender",

			/* Other */
			"Tiger and Bunny", "Blue Dragon", "Cardcaptor Sakura", "Digimon",
			"Star Wars", "Yu-Gi-Oh", "Wakfu", "Totally Spies",
			"Asterix and Obelix", "Ikkitousen", "Tenjo Tenge",
			"Highschool of the dead", "Guilty Gear", "Fullmetal Alchemist",
			"My little sister can't be this cute", "Queen's blade",
			"Oh My Goddess", "The Secret of Blue Water", "Seirei no Moribito",
			"The Road to El Dorado", "Aladdin", "Neon Genesis Evangelion",
			"Escaflowne", "Buffy the Vampire Slayer", "Streets of Rage",
			"K-On", "Love Hina", "Samurai Champloo", "Battle Arena Toshinden",
			"Fire Emblem", "FLCL", "Code Geass", "Inspector Gadget",
			"Scooby-Doo", "The Incredibles", "Maison Ikkoku",
			"Teenage Mutant Ninja Turtles", "Who Framed Roger Rabbit",
			"The Neverending Story", "Mahou Shoujo Lyrical Nanoha ViVid",
			"Game of Thrones", "Idolmaster", "Vocaloid",
			"James Cameron's Avatar", "Bakusou Kyoudai Let’s and Go", "Gundam",
			"Barbie", "The Simpsons", "Futurama", "Modern Family",

			/*
			 * Disney
			 */
			"Kim Possible", "Little Red Riding Hood", "Hercules", "Peter Pan",
			"Alice in Wonderland", "Rapunzel", "Ben 10",
			"Beauty and the Beast", "Snow White and the Seven Dwarfs",
			"The Little Mermaid", "Cinderella", "Disney",
			/* DBZ */
			"Dragon Ball GT", "Dragon Ball", "Darkstalkers", "Dead or Alive",

			/* # Games # */
			"Mortal Kombat", "Soul Calibur", "Legend of Valkyrie",
			"Metal Slug", "Resident Evil", "Tekken", "Street Fighter",
			"Legend of Zelda", "Grandia", "Tomb Raider", "Rival Schools",
			"Breath of Fire", "World of Warcraft", "Star Ocean",
			"Super Mario Bros.", "The Tower of Druaga", "Xenosaga",
			"Xenogears", "League of Legends", "Dead Rising",
			"Heroes of Might and Magic", "Assassins Creed", "Dark Souls",
			"Metroid", "Kingdom Hearts", "Megami Tensei", "Valkyrie Profile",
			"Mirror's Edge", " Ragnarok Online",

			/* Draque */
			"Dragon Quest XIII", "Dragon Quest XII", "Dragon Quest XI",
			"Dragon Quest X", "Dragon Quest IX", "Dragon Quest VIII",
			"Dragon Quest VII", "Dragon Quest VI", "Dragon Quest V",
			"Dragon Quest IV", "Dragon Quest III", "Dragon Quest II",
			"Dragon Quest",

			/* KOF */
			"Art of Fighting", "Fatal Fury", "King of Fighters",

			/* Final fantasy */
			"Final Fantasy XIII", "Final Fantasy XII", "Final Fantasy XI",
			"Final Fantasy X", "Final Fantasy IX", "Final Fantasy VIII",
			"Final Fantasy VII", "Final Fantasy VI", "Final Fantasy V",
			"Final Fantasy IV", "Final Fantasy III", "Final Fantasy II",

			"Final Fantasy Tactics", "Final Fantasy" };

	public static void main(String[] args) {

		MainCollectionManager fw = new MainCollectionManager();
		fw.walk(mediaDir);
	}

	public void walk(String path) {

		File root = new File(path);
		File[] list = root.listFiles();

		for (File f : list) {
			if (f.isDirectory()) {
				walk(f.getAbsolutePath());
				System.out.println("Dir:" + f.getAbsoluteFile());
				if (f.listFiles().length == 0) {
					try {
						FileUtils.deleteDirectory(f);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				if (f.getName().toLowerCase().endsWith("jpg")
						|| f.getName().toLowerCase().endsWith("jpeg")
						|| f.getName().toLowerCase().endsWith("png")
						|| f.getName().toLowerCase().endsWith("gif")) {
					checkCollection(f);
				}
			}
		}
	}

	public boolean checkCollection(File file) {
		String fileName = file.getName();
		String coll = getCollectionName(fileName.toLowerCase());
		if (!"".equalsIgnoreCase(coll)) {
			System.out.println("COLLECTION : " + coll);
			System.out.println(outputDir + File.separator + coll
					+ File.separator + fileName);
			try {
				FileUtils.moveFileToDirectory(file, new File(outputDir
						+ File.separator + coll + File.separator), true);
			} catch (IOException e) {
				try {
					FileUtils.moveFile(file, new File(outputDir
							+ File.separator + coll + File.separator
							+ "Copie of " + file.getName()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return true;
	}

	public String getCollectionName(String fileName) {
		String key = "";

		if ("".equalsIgnoreCase(key)) {
			for (String cat : this.listCategories) {

				if (fileName.replace(" ", "_").contains(
						cat.toLowerCase().replace(" ", "_"))) {
					key = "$" + cat;
					break;
				}
			}
		}

		if ("".equalsIgnoreCase(key)) {
			for (String cat : this.listActresses) {

				if (fileName.replace(" ", "_").contains(
						cat.toLowerCase().replace(" ", "_"))) {
					key = "." + cat;
					break;
				}
			}
		}
		if ("".equalsIgnoreCase(key)) {
			for (String art : this.listArtists) {

				if (fileName.replace(" ", "_").contains(
						art.toLowerCase().replace(" ", "_"))) {
					key = "#" + art;
					break;
				}
			}
		}
		if ("".equalsIgnoreCase(key)) {
			for (String col : this.listSeries) {
				// System.out.println(fileName + "=>"
				// + col.replace(" ", "_").toLowerCase());
				if (fileName.replace(" ", "_").contains(
						col.replace(" ", "_").toLowerCase())) {
					key = col;
					break;
				}
			}
		}
		return key;
	}

	public String[] getSeriesList() {

		SAXBuilder sxb = new SAXBuilder();

		try {
			XPath xpa = XPath.newInstance(".//serie");
			Document document = sxb.build(MainCollectionManager.class
					.getResourceAsStream("config.xml"));

			Element racine = document.getRootElement();

			List<Element> results = xpa.selectNodes(racine);
			Iterator<Element> iter = results.iterator();

			Element noeudCourant = null;
			String personneId = null;
			while (iter.hasNext()) {
				noeudCourant = iter.next();

				xpa = XPath.newInstance(".");
				personneId = xpa.valueOf(noeudCourant);

				System.out.println(personneId);

			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] series = new String[2];
		return series;
	}
}