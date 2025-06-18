# TASK 2
# Calculator

def calculator():
    while True:
        try:
            print("\nCalculator Menu:")
            print("1. Perform Calculation")
            print("2. Exit")
            menu_choice = input("Enter choice (1-2): ")

            if menu_choice == '2':
                print("Exiting calculator. Goodbye!")
                break
            elif menu_choice != '1':
                print("Invalid choice. Please select 1 or 2.")
                continue

            num1 = float(input("Enter first number: "))
            num2 = float(input("Enter second number: "))
            print("Select operation:")
            print("1. Addition (+)")
            print("2. Subtraction (-)")
            print("3. Multiplication (*)")
            print("4. Division (/)")
            choice = input("Enter choice (1-4): ")

            if choice == '1':
                result = num1 + num2
                print(f"{num1} + {num2} = {result}")
            elif choice == '2':
                result = num1 - num2
                print(f"{num1} - {num2} = {result}")
            elif choice == '3':
                result = num1 * num2
                print(f"{num1} * {num2} = {result}")
            elif choice == '4':
                if num2 != 0:
                    result = num1 / num2
                    print(f"{num1} / {num2} = {result}")
                else:
                    print("Error: Division by zero is not allowed")
            else:
                print("Invalid choice. Please select 1, 2, 3, or 4.")

        except ValueError:
            print("Error: Please enter valid numbers")

calculator()