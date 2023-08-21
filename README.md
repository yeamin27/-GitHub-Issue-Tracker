# GitHub Issue Tracker

## Overview
The GitHub Issue Tracker is a sophisticated application designed to provide a comprehensive and organized approach to managing and monitoring issues within GitHub repositories.

## Features
1. **Comprehensive Issue Overview**: Gain insights into the status of issues by viewing a comprehensive list of all issues within a GitHub repository. Stay informed about each issue's title, description, author, creation time, and current state.

2. **Detailed Issue Information**: Access in-depth details of individual issues, including vital information such as title, description, authorship, creation timestamp, and issue state. This feature empowers users with the context they need for effective issue resolution.

3. **Efficient Issue Search**: Seamlessly search for specific issues by matching keywords within their titles. This search functionality enables users to quickly pinpoint the issues that require their attention, enhancing efficiency and response times.

4. **Pagination Support**: Effectively navigate through the list of issues using pagination controls. Users can effortlessly traverse between different pages, ensuring a user-friendly experience even when dealing with a high volume of issues.

## Usage
1. **Repository Search**: On the home screen, input the GitHub repository URL and initiate the search process by clicking the "Search" button.

2. **Issue List Display**: After the repository is selected, the application presents a neatly organized list of issues. Each page displays up to 30 issues, ensuring optimal readability and usability.

3. **Navigation**: Utilize the provided navigation options, such as First, Previous, Next, and Last, to seamlessly move between pages of issues. This streamlined navigation system simplifies the process of reviewing and managing numerous issues.

4. **Issue Search**: To swiftly locate specific issues, enter relevant keywords in the search bar located at the top of the screen. Activate the search by clicking the "Search" button, allowing you to promptly identify issues that match your criteria.

5. **Issue Details**: For a comprehensive understanding of any issue, simply tap on the respective issue entry. This action opens a new screen that presents a detailed overview of the issue, empowering users to make informed decisions.

## Technology Stack
- **MVVM Architecture**: The application follows the MVVM (Model-View-ViewModel) architectural pattern, enhancing separation of concerns and facilitating a structured approach to development.
- **ViewBinding**: [ViewBinding](https://developer.android.com/topic/libraries/view-binding) streamlines UI development by providing compile-time safety and improved view access.
- **Data Collection**: The application relies on the [GitHub API](https://api.github.com) to aggregate and present issue-related data.
- **Network Communication**: [Retrofit](https://github.com/square/retrofit) serves as the communication backbone, ensuring seamless interaction with the GitHub API.
- **Image Loading**: [Glide](https://github.com/bumptech/glide) enhances the user experience by efficiently loading images into view components.
- **Markdown Rendering**: [Markwon](https://github.com/noties/Markwon) ensures accurate rendering of markdown-formatted content, promoting clear and structured issue descriptions.
