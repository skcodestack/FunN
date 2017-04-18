package github.funn.bean;

import java.util.List;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/6.
 */

public class NewsEntity extends BaseEntity {
    /*"message": "获取数据成功！",
            "result": [
    {
        "content": "  ",
            "title": "还挺逼真的",
            "updated": "2017-04-12 13:25:24.0",
            "image_url": 				 	 	"http://www.zbjuran.com/uploads/allimg/170207/10-1F20G32524442.jpg",
            "item": "2",
            "link_url": "/quweitupian/201702/83133.html",
            "path": "  ",
            "linkmd5id": "21deb567f7a35cd66d5ae87d4cb465cf"
    },
    {
        "content": "  ",
            "title": "找个靠谱的队友就是这么难~",
            "updated": "2017-04-12 13:25:24.0",
            "image_url": "http://www.zbjuran.com/uploads/allimg/170207/10-1F20G3060a64.jpg",
            "item": "2",
            "link_url": "/quweitupian/201702/83128.html",
            "path": "  ",
            "linkmd5id": "dd08fe488ebe89d7f6e5ff4150fdd4a7"
    }
    ],
            "code": "200"
*/
    private String code;
    private String message;
    private int  totalNum;
    private List<ItemEntity> result;


    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ItemEntity> getResult() {
        return result;
    }

    public void setResult(List<ItemEntity> result) {
        this.result = result;
    }
}
