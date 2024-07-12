public class SchedulePeriod extends Period {
    public Course course;

    public SchedulePeriod(Course course, Period period) {
        super(period);
        this.course = course;
    }

}