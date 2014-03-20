package cpsc599.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

import cpsc599.util.Logger;

public class Dialogue {
	private int boxWidth;
	private int boxHeight;
	private int lineWidth;
	private BitmapFont font;
	private CharSequence text;
	private CharSequence textRemains;
	private boolean visible;
	private boolean textLeft = false;
	
	public Dialogue() {
		Logger.debug("Setting up text");
		
		boxHeight = Gdx.graphics.getHeight() - 325;
		boxWidth = Gdx.graphics.getWidth() - 20;	// Adds 10px padding on left/right
		
		try {
			Logger.debug("XML Parsing");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = docFactory.newDocumentBuilder();
			Document doc = db.parse(new File("src/cpsc599/assets/Script.xml"));
			NodeList list = doc.getElementsByTagName("testing");
			Node node = list.item(0);
			//Logger.debug("Current Element :" + node.getNodeName());
			Element elem = (Element) node;
			text = elem.getTextContent();
		}
		catch(Exception e){
			e.printStackTrace();
			Logger.debug("Failed to parse XML");
		}
		
		font = new BitmapFont();
		
		font.setScale((float) 1.1);
		lineWidth = (int) (boxWidth/1.55);
		
		loadDialogue(text);
		
		this.visible = false;
	}
	
	public void loadDialogue(CharSequence dialogue) {
		String check;
		int bounds = text.length();
		int strEnd = 168;
		if (bounds > 168) {
			do {
				check = text.subSequence(0, strEnd).toString();
				strEnd++;
			}
			while (!check.endsWith(" ") && (strEnd != (bounds)));
			
			if (strEnd == bounds)
				text = text.subSequence(0, 168);
			else {
				textLeft = true;
				textRemains = text.subSequence((strEnd+1), bounds);
				text = text.subSequence(0, --strEnd);
			}
		}
		else
			textLeft = false;
	}
	
	public void loadTextRemains() {
		text = textRemains;
		loadDialogue(text);
	}
	
	public boolean checkTextLeft() {
		return textLeft;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public boolean toggleVisibility() {
		visible = !visible;
		Logger.debug("Setting visibility of Dialogue to: " + visible);
        return visible;
	}
	
	public void render(SpriteBatch batch) {
		if (!visible) return;
		
		// Background of dialogue
		ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(10, 10, boxWidth, boxHeight);
        shapeRenderer.end();

        batch.begin();
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.drawWrapped(batch, text, 150, 120, lineWidth);
        batch.end();
	}
}
