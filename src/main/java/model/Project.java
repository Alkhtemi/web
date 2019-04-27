package model;

import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *GET AND SETTERS
 * *@K
 */
@Value
public class Project  {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(User.class);

    private String nameProject;


    public Project(String nameProject){
        this.nameProject = nameProject;
    }


    public String getNameProject(){
        return this.nameProject;
    }
}
