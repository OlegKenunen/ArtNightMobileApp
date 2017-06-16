package ru.cpsmi.artnightmobileapp.datalayer;

//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Oleg on 16.06.2017.
 * oleg.kenunen@gmail.com
 * Class for save/read data from XML
 */

//@XmlRootElement(name = "museums")
public class MuseumsListWrapper {


    private List museums;

    //@XmlElement(name = "museum")
    public List getMuseums() {
        return museums;
    }

    public void setMuseums(List museums) {
        this.museums = museums;
    }
}
