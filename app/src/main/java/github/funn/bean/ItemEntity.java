package github.funn.bean;

import github.funn.adapter.RecyclerViewAdapter;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/4/12.
 */

public class ItemEntity extends BaseEntity implements RecyclerViewAdapter.Item{
     /*"content": "  ",
        "title": "还挺逼真的",
        "updated": "2017-04-12 13:25:24.0",
        "image_url": 				 	 	"http://www.zbjuran.com/uploads/allimg/170207/10-1F20G32524442.jpg",
        "item": "2",
        "link_url": "/quweitupian/201702/83133.html",
        "path": "  ",
        "linkmd5id": "21deb567f7a35cd66d5ae87d4cb465cf"
        */

    private String content;
    private String title;
    private String updated;
    private String image_url;
    private String item;
    private String link_url;
    private String path;
    private String linkmd5id;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLinkmd5id() {
        return linkmd5id;
    }

    public void setLinkmd5id(String linkmd5id) {
        this.linkmd5id = linkmd5id;
    }

    @Override
    public int getType() {
        return 2;
    }
}
