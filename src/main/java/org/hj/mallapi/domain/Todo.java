package org.hj.mallapi.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="tbl_todo")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;

    //@Column(length=500 , nullable=false)
    private String title;

    private String writer;

    private boolean complete;

    private LocalDate dueDate;

    public void changeTitle(String t) {
        this.title = t;
    }

    public void changeComplete(boolean c){
        this.complete =c;
    
    }

    public void changeDueDate(LocalDate d) {
        this.dueDate =d;
    }

    
}
