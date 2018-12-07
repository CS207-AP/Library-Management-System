# Library Management System
Please click [here](https://cs207-ap.github.io/Library-Management-system/) to view the README in proper format.

## Table of Contents

* [Project Aim](#project-aim)
* [Solution Approach](#solution-approach)
* [Challenges](#challenges)
* [Project Flow](#project-flow)
* [Documentation](#documentation)
* [Built With](#built-with)
* [Authors](#authors)
* [License](#license)
* [Acknowledgments](#acknowledgments)

## Project Aim 
This project's aim was to adopt a fully functioning independent software to manage a library and automate processes wherever possible.

## Solution Approach
Once getting the project aim in place we went about listing the various components of the project. In short, it had to comprise mainly of three things: a flexible way to manage the GUI, a query oriented language for a database, and a primary language that would handle the back-end. This primary language had to manage the back-end by communicating between the GUI and the database.

Java was chosen as the primary language as all 3 contributors were well versed with it and its vast array of libraries suited our need perfectly.
MySQL was chosen as the database management system as it provided a simple syntax and because the contributors  had prior knowledge of the language.
After a lot of back and forth, from trying Java Swing to JavaFx, we finally settled on using a web based interface for the following reasons:

* Bootstrap (a web UI framework) along with Pingendo (a bootstrap based GUI builder) made designing the front-end easier
* Cross platform uniformity (Windows, OSX and Linux\_64)
* It looks prettier, unlike Swing which makes you feel like you're using Windows98

Finally, we decided on using Apache Tomcat v9.0 as the servlet container for the GUI as it was the most easy-to-configure container which worked well out of the box.

For more information about the build tools refer to the [Built With](#built-with) section.

* We split the work in such a way that we worked on our own part independent of the other. This helped because each person was well versed with specific parts of the project and this also held each of us accountable. It also reduced the amount of merge conflicts we had to deal with.

For more information refer to the [Authors](#authors) section.

## Challenges

* Web design was something none of us had experience in, therefore designing the GUI required a lot of attention.
* Deciding on which table needed what fields in the database as per our requirements (which kept changing as we progressed with the project).
* Due to the way we split the work, when the time came for our code to work in tandem, debugging was a PAIN and required all three of us to be present at the same place to identify *where* the bug was and squash it accordingly.

## Major Bugs

* Searching: The searchBooks function in the DbConnector class proved to be an interesiting bug.This was because it was one of the places where there was an explicit convergence of all the three languages we were using,java,sql and java script. The search input was taken from the field using javascript, which was then bundled into an object using java and then sent to the database using MySQL. We were hoping to find a java equivalent of the java String function contains() in SQL, however to our surprise it did not exist. Therefore we had to make use of a substring method LOCATE() instead. Also, while checking whether the input fields were NULL, it came to our attention that unlike Java where "" or NULL could both be treated as NULL, SQL treats them differently which was interesting to see. The final query can be viewed [here](https://github.com/CS207-AP/Library-Management-system/blob/master/src/dao/DBConnector.java#L966)
* Logout: We had a lot of problem trying to invalidate and cancel the session and redirect it to the login page without allowing the user to simply click the back button to go back to the homepage. After a lot of research we decided we had to clear the cache whenever the login page is loaded which prevented us from going back once we were logged out.
* Modals: In edit details of the books and users, we had a lot of problem trying to bring the existent information into the modal so that it is easier for the admin to edit the values. After a lot of time and different jquery and javascript functions we were able to bring the information to display (and later realized that it was because the script tag for jquery was after the actual jqeury code!)

## Project Flow

![flow](/docs/flow.png)

## Documentation
* [JAVA Docs](http://htmlpreview.github.com/?https://github.com/CS207-AP/Library-Management-system/blob/master/docs/overview-summary.html)
* [JSP docs](http://htmlpreview.github.com/?https://github.com/CS207-AP/Library-Management-system/blob/master/docs/jspDoc.html)

## Built With

* [Java](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) - Primary Language
* [Bootstrap](http://getbootstrap.com/) - Web Content 
* [Pingendo](https://pingendo.com/) - Bootstrap Builder 
* [MySQL](https://www.mysql.com/) - Database Management System
* [Apache Tomcat](https://tomcat.apache.org/download-90.cgi) - Java Servlet Container

## What could have been better?

* Searching: We could have made it much easier for the users to search for books by categories instead of having to type in the search preferences. We could have also had a sorting procedure for alphabetically displaying the books.
* Fine system: Made the fine system more formal and more interactive for the user wherein they have to click and pay the fine (in the code we have shared - we have hardcoded the dates in a way that on returning you get a fine for returning the book 4 days after the due date).
* Database: We could have perhaps populated a larger database to better exhibit the features of the code.
* Configuration: Perhaps we could have made an executable (of sorts) that would run on less-complex configurations and made the application more portable. 

## Authors

### Aastha Shah 
* Web Content and GUI design
* [View Contributions](https://github.com/CS207-AP/Library-Management-system/tree/master/WebContent)
* [github](https://github.com/aastha-shah)
* Report - Flowchart and limitations/what could have been better

### Nandini Agrawal
* Servlet and Objects
* [View Contributions](https://github.com/CS207-AP/Library-Management-system/tree/master/src/servlet)
* [View Contributions](https://github.com/CS207-AP/Library-Management-system/tree/master/src/objects)
* [github](https://github.com/Nandini18)
* Report - Aims and solutions approach 

### Reuel John 
* Database Management and Query Handling
* [View Contributions](https://github.com/CS207-AP/Library-Management-system/tree/master/src/dao)
* [github](https://github.com/mojoman11)
* Report - Documentation and challenges

See also the list of [contributors](https://github.com/CS207-AP/Library-Management-system/graphs/contributors) who participated in this project.

## License

This project is licensed under the GNU GPLv3 License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Professor Goutam Paul
* Barun Parruck (TA)
* Sanchit Bansal (TA)

