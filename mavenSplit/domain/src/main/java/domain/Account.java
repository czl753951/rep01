package domain;

/**
 * @Description: java类作用描述
 * @Author: 阿呆
 * @CreateDate: 2019/1/8 18:51
 * @Version: 1.0
 */
public class Account {

    private Integer id;
    private String name;
    private Double money;

    public Account() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
