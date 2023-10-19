package com.cheatbreaker.client.audio.music.data;

import com.cheatbreaker.client.audio.music.util.DashUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.util.ResourceLocation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

@Getter @Setter
@RequiredArgsConstructor
public class Station {
    private final String name;
    private final String logoURL;
    private final String genre;
    private final String currentSongURL;
    private final String streamURL;

    private String title;
    private String artist;
    private String album;
    private String coverURL = "";

    private boolean favourite;
    public boolean play;

    private LocalDateTime startTime;

    private int duration;

    public ResourceLocation currentResource;
    public ResourceLocation previousResource;

    /**
     * Ends the stream.
     */
    public void endStream() {
        DashUtil.end(DashUtil.get(this.streamURL));
    }

    /**
     * Gets the music player's music list data.
     */
    public void getData() {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.getCurrentSongURL());
            NodeList nodeList = document.getElementsByTagName("playlist");

            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                Element element = (Element) node;

                this.setTitle(this.getElement(element, "title"));
                this.setArtist(this.getElement(element, "artist"));
                this.setAlbum(this.getElement(element, "album"));
                this.setCoverURL(this.getElement(element, "cover"));
                this.setDuration(Integer.parseInt(this.getElement(element, "duration")));

                String programStartTS = this.getElement(element, "programStartTS");
                String dateFormat = "dd MMM yy hh:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                if (this.currentResource != null && !("client/songs/" + this.getTitle()).equals(this.currentResource.getResourcePath())) {
                    this.previousResource = this.currentResource;
                    this.currentResource = null;
                }

                try {
                    Date date = simpleDateFormat.parse(programStartTS);
                    this.setStartTime(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (this.play) {
            this.play = false;
            this.endStream();
        }
    }

    /**
     * Retrieves the element.
     */
    private String getElement(Element element, String string) {
        try {
            NodeList nodeList = element.getElementsByTagName(string);
            Element element2 = (Element) nodeList.item(0);

            return element2.getChildNodes().item(0).getNodeValue().trim();
        } catch (Exception exception) {
            return "";
        }
    }

    public String toString() {
        return "Station(streamUrl=" + this.getStreamURL() + ", currentSongUrl=" + this.getCurrentSongURL() + ", genre=" + this.getGenre() + ", logoUrl=" + this.getLogoURL() + ", name=" + this.getName() + ", favorite=" + this.isFavourite() + ", startTime=" + this.getStartTime() + ", title=" + this.getTitle() + ", artist=" + this.getArtist() + ", album=" + this.getAlbum() + ", coverUrl=" + this.getCoverURL() + ", duration=" + this.getDuration() + ", RESOURCE_CURRENT=" + this.getCurrentResource() + ", RESOURCE_PREVIOUS=" + this.getPreviousResource() + ", play=" + this.isPlay() + ")";
    }
}
