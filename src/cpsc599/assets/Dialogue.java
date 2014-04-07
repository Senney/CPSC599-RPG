package cpsc599.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import cpsc599.util.SharedAssets;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cpsc599.util.Logger;

public class Dialogue {
	private int boxWidth;
	private int boxHeight;
	private int lineWidth;
	private BitmapFont font;
    private List<String> strings;
    private int dialogueStep;
	private CharSequence text;
	private CharSequence textRemains;
	private boolean visible;
	private boolean textLeft = false;

    private Document doc;

    private float displayTime;
    private Texture portrait;

    public Dialogue() {
		Logger.debug("Setting up text");
		
		boxHeight = (int)round16(Gdx.graphics.getHeight() - 325);
		boxWidth = (int)round16(Gdx.graphics.getWidth() - 20);	// Adds 10px padding on left/right
		
		font = SharedAssets.font_14;
		
		font.setScale((float) 1.1);
		lineWidth = (int) (boxWidth/1.2);

        displayTime = 0.f;

        strings = new ArrayList<String>();
        dialogueStep = 0;

		this.visible = false;
        this.portrait = SharedAssets.defaultPortrait;
	}

    public boolean loadDialogueXML(String xmlFile) {
        if (doc == null) {
            try {
                Logger.debug("Parsing XML from " + xmlFile);
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = docFactory.newDocumentBuilder();
                doc = db.parse(new File(xmlFile));
            }
            catch(Exception e){
                e.printStackTrace();
                Logger.debug("Failed to parse XML");
                return false;
            }
        }

        return true;
    }

    public void showDialogueTime(float curTime, float showTime) {
        if (!this.visible) {
            this.displayTime = curTime + showTime;
            this.visible = true;
        }

        if (curTime > this.displayTime) {
            this.visible = false;
        }
    }

    public void display(String text, Texture portrait) {
        this.setDialogueText(text);
        this.setVisibility(true);
        this.setPortrait(portrait);
    }

    public void display(String text) {
        display(text, SharedAssets.defaultPortrait);
    }

    public void setDialogueText(String text) {
        this.text = text;
        loadDialogue();
    }

    public void setDialogueTag(String tagName) {
        strings.clear();
        dialogueStep = 0;

        NodeList list = doc.getElementsByTagName(tagName);
        Node node = list.item(0);
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            String text = children.item(i).getTextContent().trim();
            if (text.length() == 0) continue;
            strings.add(children.item(i).getTextContent());
        }

        stepDialogue();

        loadDialogue();
    }

    public boolean stepDialogue() {
        if (dialogueStep == strings.size()) return false;

        this.text = strings.get(dialogueStep++);
        loadDialogue();
        return true;
    }
	
	public void loadDialogue() {
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

        this.boxHeight = (int)round16(font.getWrappedBounds(this.text, lineWidth).height) + 32;
	}
	
	public void loadTextRemains() {
		text = textRemains;
        loadDialogue();
	}
	
	public boolean checkTextLeft() {
		return textLeft;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public boolean toggleVisibility() {
		return setVisibility(!this.visible);
	}

    public boolean setVisibility(boolean b) {
        this.visible = b;
        Logger.debug("Setting visibility of Dialogue to " + visible);
        return visible;
    }
	
	public void render(SpriteBatch batch) {
		if (!visible) return;

        batch.begin();
        drawTextBackdrop(batch, 10, 10, boxWidth, boxHeight);
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        BitmapFont.TextBounds bounds = font.getWrappedBounds(this.text, this.lineWidth);
        batch.draw(this.portrait, 20, 24 + bounds.height);
		font.drawWrapped(batch, text, 64, 32 + bounds.height, lineWidth);
        batch.end();
	}

    private float round16(float value) {
        if (value % 16 != 0) {
            int numDiv = (int)(value / 16.f);
            value = (numDiv+1) * 16;
        }

        return value;
    }

    private void drawTextBackdrop(SpriteBatch batch, int xpos, int ypos, int width, int height) {
        if (width % 16 != 0 || height % 16 != 0) {
            Logger.fatal("Unable to draw menu with non-16-divisible width and height.");
            return;
        }

        int width_iter = width / 16;
        int height_iter = height / 16;

        // Draw the bottom and top
        batch.draw(SharedAssets.menu_texture[2][0], xpos, ypos);
        batch.draw(SharedAssets.menu_texture[0][0], xpos, ypos + (16 * (height_iter-1)));
        for (int i = 1; i < width_iter - 1; i++) {
            batch.draw(SharedAssets.menu_texture[2][1], xpos+(16*i), ypos);
            batch.draw(SharedAssets.menu_texture[0][1], xpos+(16*i), ypos + (16 * (height_iter-1)));
        }
        batch.draw(SharedAssets.menu_texture[2][2], xpos + (16 * (width_iter-1)), ypos);
        batch.draw(SharedAssets.menu_texture[0][2], xpos + (16 * (width_iter-1)), ypos + (16 * (height_iter-1)));

        // Draw the middle
        for (int i = 1; i < height_iter - 1; i++) {
            batch.draw(SharedAssets.menu_texture[1][0], xpos, ypos + (16 *i));
            for (int j = 1; j < width_iter - 1; j++) {
                batch.draw(SharedAssets.menu_texture[1][1], xpos + (16*j), ypos + (16*i));
            }
            batch.draw(SharedAssets.menu_texture[1][2], xpos + (16 * (width_iter - 1)), ypos + (16*i));
        }
    }

    public void setPortrait(Texture portrait) {
        this.portrait = portrait;
    }
}
