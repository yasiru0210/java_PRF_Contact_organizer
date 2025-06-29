# iFRIEND Contact Organizer - GUI Version

A modern Java Swing GUI application for managing contacts efficiently. This application provides an intuitive graphical interface for adding, updating, deleting, searching, and organizing contacts.

## Features

### Core Functionality
- **Add Contacts**: Create new contacts with comprehensive details
- **Update Contacts**: Modify existing contact information
- **Delete Contacts**: Remove contacts with confirmation dialog
- **Search Contacts**: Real-time search functionality across all contact fields
- **Sort Contacts**: Sort by name, salary, or birthday with one click

### User Interface
- **Modern GUI**: Clean, professional interface built with Java Swing
- **Responsive Design**: Resizable window with minimum size constraints
- **Color-coded Buttons**: Intuitive color scheme for different actions
- **Data Table**: Sortable table with column headers and row selection
- **Form Dialogs**: Modal dialogs for adding and updating contacts
- **Real-time Search**: Filter contacts as you type

### Data Validation
- **Phone Number**: Must be 10 digits starting with '0'
- **Salary**: Must be a positive number
- **Birthday**: Must be in YYYY-MM-DD format and not in the future
- **Required Fields**: All fields must be filled before saving

## Technical Details

### Architecture
- **Object-Oriented Design**: Clean separation with Contact model class
- **Event-Driven**: Responsive GUI with proper event handling
- **MVC Pattern**: Separation of data, view, and control logic

### Components Used
- `JFrame` - Main application window
- `JTable` with `DefaultTableModel` - Contact data display
- `TableRowSorter` - Sorting and filtering functionality
- `JDialog` - Modal forms for add/update operations
- `GridBagLayout` - Professional form layouts
- `BorderLayout` - Main window organization

## Requirements

- Java Development Kit (JDK) 11 or higher
- Maven 3.6 or higher (for building)

## How to Run

### Using Maven (Recommended)

1. Clone or download the project
2. Navigate to the project directory
3. Run the application:
   ```bash
   mvn clean compile exec:java
   ```

### Building Executable JAR

1. Build the JAR file:
   ```bash
   mvn clean package
   ```

2. Run the JAR file:
   ```bash
   java -jar target/contact-organizer-gui-1.0.0.jar
   ```

### Direct Compilation

1. Compile the Java files:
   ```bash
   javac -d target/classes src/main/java/com/ifriend/*.java
   ```

2. Run the application:
   ```bash
   java -cp target/classes com.ifriend.ContactOrganizerGUI
   ```

## Usage Guide

### Main Window
- **Header**: Application title and search field
- **Table**: Displays all contacts with sortable columns
- **Buttons**: Action buttons for managing contacts

### Adding Contacts
1. Click "Add Contact" button
2. Fill in all required fields in the dialog
3. Click "Add Contact" to save or "Cancel" to abort

### Updating Contacts
1. Select a contact from the table
2. Click "Update Contact" button
3. Modify the fields in the dialog
4. Click "Update Contact" to save changes

### Deleting Contacts
1. Select a contact from the table
2. Click "Delete Contact" button
3. Confirm the deletion in the dialog

### Searching Contacts
- Type in the search field at the top right
- The table will filter results in real-time
- Search works across all contact fields

### Sorting Contacts
- Click column headers to sort by that column
- Use the sort buttons for quick sorting:
  - "Sort by Name" - Alphabetical order
  - "Sort by Salary" - Ascending salary order
  - "Sort by Birthday" - Chronological order

## Data Format

### Contact Fields
- **ID**: Auto-generated (C0001, C0002, etc.)
- **Name**: Contact's full name
- **Phone Number**: 10-digit number starting with 0
- **Company**: Company name
- **Salary**: Positive decimal number
- **Birthday**: Date in YYYY-MM-DD format

### Validation Rules
- All fields are required
- Phone numbers must match pattern: 0xxxxxxxxx
- Salary must be greater than 0
- Birthday must be a valid date not in the future

## Color Scheme

- **Header**: Professional blue (#2980b9)
- **Add Button**: Success green (#2ecc71)
- **Update Button**: Info blue (#3498db)
- **Delete Button**: Danger red (#e74c3c)
- **Sort Buttons**: Various accent colors for easy identification

## Future Enhancements

Potential improvements for future versions:
- Data persistence (file or database storage)
- Import/Export functionality (CSV, JSON)
- Advanced search filters
- Contact categories/groups
- Photo support for contacts
- Backup and restore functionality
- Print contact lists
- Contact statistics and reports

## License

This project is open source and available under the MIT License.