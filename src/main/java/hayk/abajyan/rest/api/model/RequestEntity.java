package hayk.abajyan.rest.api.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "requests")
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;



    @Column(name = "request_date")
    private LocalDate requestDate;


    @ElementCollection(targetClass=String.class)
    @CollectionTable(name = "params_list", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "request_params")
    private List<String> requestParams;

    @Column(name = "group_name")
    private String groupName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }


    public void setRequestParams(List<String> requestParams) {
        this.requestParams = requestParams;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public List<String> getRequestParams() {
        return requestParams;
    }
}
