/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Iuri
 */
@Value
public class Milestones {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Milestones.class);

    private String nameMilestone;
    private String description;
    private String dueDate;
    private String finishDate;

    public Milestones(String nameMilestone, String description, String dueDate, String finishDate) {
        this.nameMilestone = nameMilestone;
        this.description = description;
        this.dueDate = dueDate;
        this.finishDate = finishDate;
    }

    public String getNameMilestone() {
        return this.nameMilestone;
    }

    public String getDescription() {
       return description;
   }

    public String getDueDate(){
        return dueDate;
    }

    public String getFinishDate(){
        return finishDate;
    }
}
