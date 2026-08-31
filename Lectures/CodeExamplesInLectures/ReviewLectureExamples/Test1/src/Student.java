class Student {
    private int id;

    public Student(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;           // same reference
        if (obj == null) return false;
        if (!(obj instanceof Student)) return false;

        Student other = (Student) obj;
        return this.id == other.id;
    }
}
