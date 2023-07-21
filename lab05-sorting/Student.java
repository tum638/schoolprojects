public class Student {
	// the name of a student
	private String name;

	// the student's campus address
	private String campusAddress;

	// the student's campus telephone number
	private Long campusTelephoneNumber;

	// the student's SU box number
	private Short SUboxNumber;

	// the students home phone number
	private Long homePhoneNumber;

	/**
	 * A function that initializes a student object
	 * 
	 * @param name                  the name of the student
	 * @param campusAddress         the campus address of the student
	 * @param campusTelephoneNumber the campus telephone of the student
	 * @param SUboxNumber           the SU box number of the student
	 * @param homePhoneNumber       the home phone number of the student
	 */
	public Student(String name, String campusAddress, Long campusTelephoneNumber, Short SUboxNumber,
			Long homePhoneNumber) {
		this.name = name;
		this.campusAddress = campusAddress;
		this.campusTelephoneNumber = campusTelephoneNumber;
		this.SUboxNumber = SUboxNumber;
		this.homePhoneNumber = homePhoneNumber;
	}

	/**
	 * A function that prints out the string representation of the student
	 */
	public String toString() {
		return this.name;
	}

	/**
	 * a getter method for the campus address
	 *
	 * @return the campus address of a student
	 */
	public String getCampusAddress() {
		return this.campusAddress;
	}

	/**
	 * a getter method for the telephone number
	 * 
	 * @return the campus telephone number of a student
	 */
	public Long getCampusTelephoneNumber() {
		return this.campusTelephoneNumber;
	}

	/**
	 * A getter method for the student SU box
	 * 
	 * @return the SU box for the student
	 */
	public Short getSUboxNumber() {
		return this.SUboxNumber;
	}

	/**
	 * A getter method for the student home phone number
	 * 
	 * @return the student home phone number
	 */
	public Long getHomePhoneNumber() {
		return this.homePhoneNumber;
	}

}
