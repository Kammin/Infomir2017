package kamin.com.infomir2017.Model;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DataHolder {
    public static RSS rss;
    public static List<RSS> rssList;
    public static String curentCharset = StandardCharsets.UTF_8.displayName();
    public static String encoding = "UTF-8";


    public static boolean parseSringRSS(String xml, String URL) {
        Log.d("start", "curentCharset " + curentCharset + "  encoding " + encoding);
        String firstLine = xml.substring(0, 100);
        int start = firstLine.indexOf("encoding=") + 10;
        if (start > 0) {
            encoding = firstLine.substring(start, firstLine.indexOf(firstLine.charAt(start -1), start ));
        }
        if (!curentCharset.equals(encoding))
            try {
                byte[] ptext = xml.getBytes(curentCharset);
                xml = new String(ptext, encoding);
                Log.d("parseSringRSS", "Convert response charset from " + curentCharset + "  to " + encoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        Log.d("start", "curentCharset " + curentCharset + "  encoding " + encoding);
        Boolean header = true;
        RSS rssTest = new RSS();
        Item item = new Item();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xml));
            String xppName = "";
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (xpp.getEventType()) {
                    case XmlPullParser.START_DOCUMENT:
                        //Log.d(TAG, "START_DOCUMENT");
                        break;
                    case XmlPullParser.START_TAG: {
                        xppName = xpp.getName();
                        if (xppName.equals("item")) {
                            item = new Item();
                            header = false;
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        xppName = "";
                        if (xpp.getName().equals("item")) {
                            rssTest.item.add(item);
                        }
                        break;
                    }
                    case XmlPullParser.TEXT: {
                        if (header)
                            switch (xppName) {
                                case ("title"): {
                                    rssTest.title = xpp.getText();
                                    break;
                                }
                                case ("description"): {
                                    rssTest.description = xpp.getText();
                                    break;
                                }
                                case ("link"): {
                                    rssTest.link = xpp.getText();
                                    break;
                                }
                            }
                        else
                            switch (xppName) {
                                case ("title"): {
                                    item.title = xpp.getText();
                                    break;
                                }
                                case ("description"): {
                                    item.description = xpp.getText();
                                    break;
                                }
                                case ("link"): {
                                    item.link = xpp.getText();
                                    break;
                                }
                            }
                        break;
                    }
                    default:
                        break;
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //if((!rssTest.title.equals(""))&& (!rssTest.description.equals(""))&& (!rssTest.link.equals(""))&& (rssTest.item.size()!=0)){
        if ((!rssTest.title.equals("")) && (!rssTest.link.equals("")) && (rssTest.item.size() != 0)) {
            Log.d("parseSringRSS", "Parsing SUCCESS  rss title " + rssTest.title + " itemsCount " + rssTest.item.size());

            rssTest.RSSLink = URL;
            rss = rssTest;
            return true;
        } else {
            Log.d("parseSringRSS", "Parsing ERROR title" + rssTest.title + " description " + rssTest.description + " link " + rssTest.link + " size " + rssTest.item.size());
            Log.d("parseSringRSS", "Parsing ERROR");
            return false;
        }
    }
}
