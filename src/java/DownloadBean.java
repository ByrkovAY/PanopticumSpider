/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author BirkovAY
 */
@ManagedBean
@ApplicationScoped
public class DownloadBean {

    /**
     * Creates a new instance of DownloadBean
     */
    private String downloadStatus;

    public String getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(String downloadStatus) {
        this.downloadStatus = downloadStatus;
    }
    
    public DownloadBean() {
    }
    
    public String getStatus() {
        return "Статус не определен!";
    }
}
