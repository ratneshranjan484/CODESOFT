# TASK 1
# To Do List (GUI)

import tkinter as tk
from tkinter import messagebox, ttk
import random

class TodoApp:
    def __init__(self, root):
        self.root = root
        self.root.title("To-Do List")
        self.root.geometry("800x600")
        self.tasks = []

        # Button Color
        self.root.configure(bg="#f0f0f0")
        self.button_colors = {
            "view": "#4CAF50",    # Green
            "add": "#2196F3",     # Blue
            "update": "#7707FF",   # Amber
            "delete": "#F44336",   # Red
            "clear": "#9E9E9E"    # Grey
        }
        self.title_label = tk.Label(root, text="To-Do List", font=("Arial", 20, "bold"), bg="#f0f0f0", fg="#333")
        self.title_label.pack(pady=10)

        self.task_frame = tk.Frame(root, bg="#f0f0f0")
        self.task_frame.pack(pady=10, padx=10, fill=tk.BOTH, expand=True)

        self.task_listbox = tk.Listbox(self.task_frame, font=("Arial", 12), height=15, width=50, bg="#ffffff", fg="#333")
        self.task_listbox.pack(side=tk.LEFT, fill=tk.BOTH, expand=True, padx=(0, 5))

        self.scrollbar = tk.Scrollbar(self.task_frame, orient=tk.VERTICAL)
        self.scrollbar.config(command=self.task_listbox.yview)
        self.task_listbox.config(yscrollcommand=self.scrollbar.set)
        self.scrollbar.pack(side=tk.RIGHT, fill=tk.Y)

        self.input_frame = tk.Frame(root, bg="#f0f0f0")
        self.input_frame.pack(pady=10)

        tk.Label(self.input_frame, text="Task Description:", font=("Arial", 12), bg="#f0f0f0").grid(row=0, column=0, padx=5)
        self.desc_entry = tk.Entry(self.input_frame, font=("Arial", 12), width=40)
        self.desc_entry.grid(row=0, column=1, padx=5)

        # Buttons
        self.button_frame = tk.Frame(root, bg="#f0f0f0")
        self.button_frame.pack(pady=10)

        tk.Button(self.button_frame, text="View Tasks", font=("Arial", 12), bg=self.button_colors["view"], fg="white", 
                  command=self.view_tasks).grid(row=0, column=0, padx=5, pady=5)
        tk.Button(self.button_frame, text="Add Task", font=("Arial", 12), bg=self.button_colors["add"], fg="white", 
                  command=self.add_task).grid(row=0, column=1, padx=5, pady=5)
        tk.Button(self.button_frame, text="Update Task", font=("Arial", 12), bg=self.button_colors["update"], fg="white", 
                  command=self.update_task).grid(row=0, column=2, padx=5, pady=5)
        tk.Button(self.button_frame, text="Delete Task", font=("Arial", 12), bg=self.button_colors["delete"], fg="white", 
                  command=self.delete_task).grid(row=0, column=3, padx=5, pady=5)
        tk.Button(self.button_frame, text="Clear Input", font=("Arial", 12), bg=self.button_colors["clear"], fg="white", 
                  command=self.clear_input).grid(row=0, column=4, padx=5, pady=5)

    def view_tasks(self):
        self.task_listbox.delete(0, tk.END)
        if not self.tasks:
            self.task_listbox.insert(tk.END, "No tasks in the list.")
        else:
            for i, task in enumerate(self.tasks, 1):
                status = "Completed" if task["completed"] else "Pending"
                self.task_listbox.insert(tk.END, f"{i}. {task['description']} - {status}")

    def add_task(self):
        description = self.desc_entry.get().strip()
        if description:
            self.tasks.append({"description": description, "completed": False})
            self.view_tasks()
            self.clear_input()
            messagebox.showinfo("Success", f"Task '{description}' added successfully.")
        else:
            messagebox.showerror("Error", "Task description cannot be empty.")

    def update_task(self):
        try:
            selected = self.task_listbox.curselection()
            if not selected:
                messagebox.showerror("Error", "Please select a task to update.")
                return
            index = selected[0] + 1
            if 1 <= index <= len(self.tasks):
                update_window = tk.Toplevel(self.root)
                update_window.title("Update Task")
                update_window.geometry("400x300")
                update_window.configure(bg="#f0f0f0")

                tk.Label(update_window, text="Update Task", font=("Arial", 16, "bold"), bg="#f0f0f0").pack(pady=10)

                tk.Label(update_window, text="New Description:", font=("Arial", 12), bg="#f0f0f0").pack()
                desc_entry = tk.Entry(update_window, font=("Arial", 12), width=30)
                desc_entry.pack(pady=5)
                desc_entry.insert(0, self.tasks[index-1]["description"])

                tk.Label(update_window, text="Status:", font=("Arial", 12), bg="#f0f0f0").pack()
                status_var = tk.StringVar(value="Pending")
                status_menu = ttk.Combobox(update_window, textvariable=status_var, values=["Pending", "Completed"], font=("Arial", 12))
                status_menu.pack(pady=5)
                status_menu.set("Completed" if self.tasks[index-1]["completed"] else "Pending")

                def save_update():
                    new_desc = desc_entry.get().strip()
                    if not new_desc:
                        messagebox.showerror("Error", "Description cannot be empty.")
                        return
                    self.tasks[index-1]["description"] = new_desc
                    self.tasks[index-1]["completed"] = (status_var.get() == "Completed")
                    self.view_tasks()
                    update_window.destroy()
                    messagebox.showinfo("Success", f"Task {index} updated successfully.")

                tk.Button(update_window, text="Save", font=("Arial", 12), bg=self.button_colors["add"], fg="white", 
                          command=save_update).pack(pady=10)
            else:
                messagebox.showerror("Error", "Invalid task number.")
        except Exception as e:
            messagebox.showerror("Error", "Please select a valid task.")

    def delete_task(self):
        try:
            selected = self.task_listbox.curselection()
            if not selected:
                messagebox.showerror("Error", "Please select a task to delete.")
                return
            index = selected[0] + 1
            if 1 <= index <= len(self.tasks):
                removed_task = self.tasks.pop(index-1)
                self.view_tasks()
                messagebox.showinfo("Success", f"Task '{removed_task['description']}' deleted successfully.")
            else:
                messagebox.showerror("Error", "Invalid task number.")
        except Exception as e:
            messagebox.showerror("Error", "Please select a valid task.")

    def clear_input(self):
        self.desc_entry.delete(0, tk.END)

def main():
    root = tk.Tk()
    app = TodoApp(root)
    root.mainloop()

if __name__ == "__main__":
    try:
        main()
    except EOFError:
        print("Input terminated. Exiting To-Do List Application.")
    except Exception as e:
        print(f"An error occurred: {e}")