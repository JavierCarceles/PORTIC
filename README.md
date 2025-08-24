This project is a microservice developed for PORTIC that handles automated email notifications related to the management of dangerous goods programming. Implemented in Java with Spring, it creates and sends emails about incidences and expired schedules, ensuring that the correct users receive timely alerts.

The service processes two main types of data inputs: incidence reports and expiration notifications. It sorts, filters, and organizes programming data, builds customized email content, and sends emails using SMTP with support for multiple recipients per user. The service ensures no duplicated emails are sent to the same user.

Key features include:

Handling incidences in dangerous goods programming with detailed email content.

Managing expiration alerts with specific messages for terrestrial and maritime cases.

Support for multiple email addresses per user.

Robust email sending with error handling and logging.

Integration with configuration properties for SMTP settings.

This microservice improves PORTIC's operational communication by automating critical notifications related to hazardous goods scheduling.
