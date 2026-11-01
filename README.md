# FinTrack Home Budget Tracker - Backend

**Student:** Tabitha Kunda  
**Student ID:** 27684  
**Course:** Web Technologies  
**Institution:** AUCA (Adventist University of Central Africa)

---

## What is this project?

This is my Web Technologies midterm project. I built a REST API using Spring Boot that helps a household manage their money. The idea is simple — a family can track their expenses, see which bank accounts they use, and keep receipts for things they have already paid for.

---

## Tools I used

- Java 17
- Spring Boot 4.1.1
- Spring Data JPA (to talk to the database)
- PostgreSQL (the database)
- Maven (to build and run the project)

---

## How to set up the database

Before running the project, make sure PostgreSQL is running on your computer and create a database called `fintrack_home_budget_tracker`.

The database settings I used are:
- **Database name:** fintrack_home_budget_tracker
- **Username:** postgres
- **Password:** 1234
- **Port:** 5432

---

## How to run the project

1. Make sure PostgreSQL is running
2. Clone this repository
3. Open it in IntelliJ IDEA
4. Click the green play button to run, or use this command in the terminal:

```bash
./mvnw spring-boot:run
```

Once it starts, the API will be available at `http://localhost:8080`

---

## Entities (the main things in the system)

| Entity | What it represents |
|--------|-------------|
| Bank | A bank like Bank of Kigali or Equity Bank |
| Account | A bank account that belongs to one bank |
| SpendingCategory | A label like Groceries, Transport, or Utilities |
| HouseholdMember | A person who lives in the house |
| Expense | Money a member plans to spend or has spent |
| Receipt | Proof that an expense was paid |

---

## How they connect

- A **Bank** can have one **Account**
- An **Account** can have many **SpendingCategories**
- A **HouseholdMember** can create many **Expenses**
- An **Expense** belongs to one **Member** and one **Account**
- A **Receipt** is attached to one **Expense** (only when it is PAID)

---

## API Endpoints

### Banks `/api/banks`
| Method | URL | What it does |
|--------|-----|-------------|
| POST | `/save` | Add a new bank |
| GET | `/all` | Get all banks |
| GET | `/{id}` | Get one bank by ID |
| PUT | `/update/{id}` | Update a bank |
| DELETE | `/delete/{id}` | Delete a bank |
| GET | `/free` | Get banks that have no account yet (C2) |

### Spending Categories `/api/spending-categories`
| Method | URL | What it does |
|--------|-----|-------------|
| POST | `/save` | Add a new spending category |
| GET | `/all` | Get all categories |
| GET | `/{id}` | Get one category by ID |
| PUT | `/update/{id}` | Update a category |
| DELETE | `/delete/{id}` | Delete a category |
| GET | `/unused` | Get categories not linked to any account (C3) |

### Household Members `/api/household-members`
| Method | URL | What it does |
|--------|-----|-------------|
| POST | `/save` | Add a new household member |
| GET | `/all` | Get all members |
| GET | `/{id}` | Get one member by ID |
| PUT | `/update/{id}` | Update a member |
| DELETE | `/delete/{id}` | Delete a member |
| GET | `/by-last-name?lastName=` | Find members by last name (B1) |
| GET | `/of-account/{accountId}` | Get members who spent on a specific account (C4) |

### Accounts `/api/accounts`
| Method | URL | What it does |
|--------|-----|-------------|
| POST | `/save` | Add a new account |
| GET | `/all` | Get all accounts |
| GET | `/{id}` | Get one account by ID |
| PUT | `/update/{id}` | Update an account |
| DELETE | `/delete/{id}` | Delete an account |
| GET | `/by-spending-category?name=` | Get accounts that use a specific category (C1) |
| PUT | `/{accountId}/spending-categories/{categoryId}` | Add a spending category to an account (D3) |
| PATCH | `/{accountId}/bank?bankCode=` | Move an account to a different bank (D4) |

### Expenses `/api/expenses`
| Method | URL | What it does |
|--------|-----|-------------|
| POST | `/save?memberId=&accountId=` | Create a new expense (D1) |
| GET | `/all` | Get all expenses |
| GET | `/{id}` | Get one expense by ID |
| PUT | `/update/{id}` | Update an expense |
| DELETE | `/delete/{id}` | Delete an expense (E3) |
| GET | `/by-status?status=` | Get expenses by their status (B2) |
| GET | `/between?start=&end=` | Get expenses between two dates (B3) |
| PATCH | `/{id}/status?status=` | Change the status of an expense (D2) |
| GET | `/stats/by-status` | Count how many expenses are in each status (F1) |
| PATCH | `/cancel-day?accountId=&date=` | Cancel all expenses for an account on a date (F2) |

### Receipts `/api/receipts`
| Method | URL | What it does |
|--------|-----|-------------|
| POST | `/save?expenseId=` | Save a receipt for a PAID expense (E1) |
| GET | `/household-member/{memberCode}` | Get all receipts for a member (E2) |
| DELETE | `/delete/{id}` | Delete a receipt (E3) |

---

## Business Rules I implemented

- **D1:** You cannot create an expense with a past date. Also, one account cannot have two expenses on the same date.
- **D2:** The expense status can only go forward: `PLANNED → PENDING → PAID`. You can also void it from PLANNED or PENDING. You cannot skip steps or go backwards.
- **D3:** You can add a spending category to an account, but you cannot remove existing ones.
- **D4:** You can only move an account to a bank that does not already have another account.
- **E1:** You can only save a receipt for an expense that is already PAID.
- **E3:** You cannot delete an expense that has a receipt attached to it.
- **F2:** Bulk cancel will set all non-PAID expenses for a given account on a specific date to VOIDED.

---

## Postman Collection

I tested all the endpoints using Postman. The collection with all requests and test data is included in this repository:

`fin-track-home-budget-tracker-midterm.postman_collection.json`
