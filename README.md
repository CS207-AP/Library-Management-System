# Library Management System

One Paragraph of project description goes here

## Project Aim 
The project's aim was to be able to adopt a fully functioning independent software to manage a library and automate whatever process wherever possible.

## Solution Approach
Once getting the project aim into place we went about listing the various components of the project.In short it had to compromise mainly of three things, a flexible way tomanage the GUI, a query oriented language for a database and a primary language that would handle the backend. This primary language had to manage the backend by communicating between the GUI and the database.

Java was chosen as the primary language as all 3 contributors were well versed with it and it's vast array of libraries suited our need perfectly.
Mysql was chosen as the database management system as it provided a simple syntax along with the contributors having prior knowledge of the language.
After a lot of back and forth, from trying swing to JavaFx we finally settled on using a web based interface for the following reasons

* Bootstrap (a web UI framework) along with Pingendo (a bootstrap based gui builder) made designing the front-end easier
* Cross platform uniformity (Windows,OSX and Linux\_64)
* It looks much more prettier unlike swing which makes you feel like you're using Windows98
* Finally, we decided on using Apapche Tomcat v9.0 as the servlet container for the GUI as it was the most easy-to-configure container which worked well out of the box.

For more information about the build tools refer to the Built With section.

* We split the work in such a way that we worked on our own part independent of the other. This helped because each person would be well versed in a certian part of the project and hence solve the problem of accountability.It also reduced the amount of merge conflicts we had to deal with.

For more information refer to the Authors section.

## Challenges

* Web design was something none of us had experience in, therefore designing the GUI required a lot of attention.
* Deciding on which table needed what fields in the database as per our requirements which kept changing as we progressed with the project.
* Due to the way we split the work, when the time came for our code to work in tandem debugging was a PAIN and required all three of us to be present at the same place to identify *where* the bug was and squash it accordingly.

## Project flow

![flow](https://github.com/CS207-AP/Library-Management-system/blob/master/docs/flow.png)

## Documentation

* Java docs
* JSP docs

## Built With

* [Java](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) - Primary Language
* [Bootstrap](http://getbootstrap.com/) - Web Content 
* [Pingendo](https://pingendo.com/) - Bootstrap Builder 
* [Mysql](https://www.mysql.com/) - Database Management System
* [Apache Tomcat](https://tomcat.apache.org/download-90.cgi) - Java Servlet Container

## Authors

* **Aastha Amul Shah** - *JSP pages* - [Aastha Shah](https://github.com/aastha-shah)
* **Nandini Agrawal** - *Servlets* - [Nandini Agrawal](https://github.com/Nandini18)
* **Reuel John** - *Database Management* - [Reuel John](https://github.com/mojoman11)

See also the list of [contributors](https://github.com/CS207-AP/Library-Management-system/graphs/contributors) who participated in this project.

## License

This project is licensed under the GNU GPLv3 License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Professor Goutam Paul
* Barun Parruck (TA)
* Sanchit Bansal (TA)
