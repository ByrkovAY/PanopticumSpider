import java.io.Serializable;  
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
  

/**
 *
 * @author BirkovAY
 */
@ManagedBean(name="counterBean",eager=true)
@SessionScoped
//public class CounterBean {  
public class CounterBean implements Serializable{  
    
    Integer count=2;  
    String response="jfjkfj";
    String about;

    public String getAbout() {
        return "about";
    }

    public String getResponse() {
        this.response="kwjfis";
        
        return "index";
    }
  
    public Integer getCount() {  
        return count;  
    }  
  
    public void setCount(Integer count) {  
        this.count = count;  
    }  
      
    public void increment() {  
        count++;  
    }  
    
     public String fetch() {
         
         return null;
     }
}  