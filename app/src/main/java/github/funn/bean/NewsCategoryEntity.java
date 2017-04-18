package github.funn.bean;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/4/12.
 */

public class NewsCategoryEntity extends BaseEntity {
    private String name;
    private String index;
    private String remark;
    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
