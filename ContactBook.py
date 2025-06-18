# TASK 5
# Contact Book

# List to store all contacts
contacts = []
# New Cntact
def add_contact():
    name = input("Enter name: ")
    phone = input("Enter phone number: ")
    email = input("Enter email: ")
    address = input("Enter address: ")

    contact = {
        "name": name,
        "phone": phone,
        "email": email,
        "address": address
    }

    contacts.append(contact)
    print("\nContact added successfully!")

# Function to view all contacts
def view_contacts():
    if not contacts:
        print("\nNo contacts found.")
        return
    
    print("\nContact List:")
    print("-------------")
    for index, contact in enumerate(contacts, 1):
        print(f"{index}. Name: {contact['name']}")
        print(f"   Phone: {contact['phone']}")
        print(f"   Email: {contact['email']}")
        print(f"   Address: {contact['address']}")
        print("-------------")

# Function to search
def search_contact():
    search_term = input("\nEnter name or phone number to search: ").lower()
    found = False
    
    for contact in contacts:
        if search_term in contact['name'].lower() or search_term in contact['phone']:
            print("\nContact Found:")
            print(f"Name: {contact['name']}")
            print(f"Phone: {contact['phone']}")
            print(f"Email: {contact['email']}")
            print(f"Address: {contact['address']}")
            found = True
    
    if not found:
        print("\nNo contact found.")

# Function to update
def update_contact():
    view_contacts()
    if not contacts:
        return
    
    try:
        index = int(input("\nEnter the contact number to update: ")) - 1
        if 0 <= index < len(contacts):
            print("\nEnter new details (leave blank to keep current value):")
            name = input(f"Name ({contacts[index]['name']}): ") or contacts[index]['name']
            phone = input(f"Phone ({contacts[index]['phone']}): ") or contacts[index]['phone']
            email = input(f"Email ({contacts[index]['email']}): ") or contacts[index]['email']
            address = input(f"Address ({contacts[index]['address']}): ") or contacts[index]['address']
            
            #Update
            contacts[index] = {
                "name": name,
                "phone": phone,
                "email": email,
                "address": address
            }
            print("\nContact updated successfully!")
        else:
            print("\nInvalid contact number.")
    except ValueError:
        print("\nPlease enter a valid number.")

def delete_contact():
    view_contacts()
    if not contacts:
        return
    
    try:
        index = int(input("\nEnter the contact number to delete: ")) - 1
        if 0 <= index < len(contacts):
            removed_contact = contacts.pop(index)
            print(f"\nContact '{removed_contact['name']}' deleted successfully!")
        else:
            print("\nInvalid contact number.")
    except ValueError:
        print("\nPlease enter a valid number.")

def main_menu():
    while True:
        print("\n=== Contact Book ===")
        print("1. Add Contact")
        print("2. View Contact List")
        print("3. Search Contact")
        print("4. Update Contact")
        print("5. Delete Contact")
        print("6. Exit")
        
        choice = input("\nEnter your choice (1-6): ")
        
        if choice == "1":
            add_contact()
        elif choice == "2":
            view_contacts()
        elif choice == "3":
            search_contact()
        elif choice == "4":
            update_contact()
        elif choice == "5":
            delete_contact()
        elif choice == "6":
            print("\nGoodbye!")
            break
        else:
            print("\nInvalid choice. Please try again.")

if __name__ == "__main__":
    main_menu()