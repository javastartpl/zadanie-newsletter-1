import java.util.Objects;

class Names {
    private String gender;
    private String firstNameChild;
    private int count;

    Names(String gender, String firstNameChild, Integer count) {
        this.gender = gender;
        this.firstNameChild = firstNameChild;
        this.count = count;
    }

    Integer getCount() {
        return count;
    }

    String getFirstNameChild() {
        return firstNameChild;
    }

    String getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Names names = (Names) o;
        return Objects.equals(firstNameChild, names.firstNameChild);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstNameChild);
    }
}
