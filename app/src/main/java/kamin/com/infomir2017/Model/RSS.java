package kamin.com.infomir2017.Model;


import java.util.ArrayList;
import java.util.List;

public class RSS {
    public Integer id;
    public String RSSLink, title, link, description, vlanguage, copyright, managingEditor, webMaster, pubDate,
            lastBuildDate, category, generator, docs, cloud, ttl, image,  rating, textInput, skipHours,
            skipDays;
    public List<Item> item;

    public RSS(){
        RSSLink="";title="";link="";description="";vlanguage="";copyright="";managingEditor="";webMaster="";pubDate="";
                lastBuildDate="";category="";generator="";docs="";cloud="";ttl="";image=""; rating="";textInput="";skipHours="";
                skipDays="";
        item = new ArrayList<Item>();
    }
    public RSS(String _title,String _link,String _RSSLink,String _description){
        RSSLink=_RSSLink;title=_title;link=_link;description=_description;vlanguage="";copyright="";managingEditor="";webMaster="";pubDate="";
        lastBuildDate="";category="";generator="";docs="";cloud="";ttl="";image=""; rating="";textInput="";skipHours="";
        skipDays="";
        item = new ArrayList<Item>();
    }
}
