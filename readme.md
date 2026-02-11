# Rewards Calculator

## Overview

This application reads customer transaction data from a JSON file and provides REST endpoints to retrieve transactions and calculate rewards points for customers by quarter.

## Rewards Calculation Rules

- Customers earn 2 points for every dollar spent over $100 in each transaction
- Customers earn 1 point for every dollar spent between $50 and $100 in each transaction
- No points are earned for amounts under $50

**Examples:**
- $120 transaction = 2×20 + 1×50 = 90 points
- $75 transaction = 1×25 = 25 points
- $40 transaction = 0 points

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/demo/
│   │       ├── model/
│   │       │   └── Transaction.java
│   │       ├── dao/
│   │       │   └── TransactionDao.java
│   │       ├── util/
│   │       │   └── DateRangeUtil.java
│   │       └── controller/
│   │           └── RewardsController.java
│   └── resources/
│       └── customer_transactions.json
```

## Data Schema

### customer_transactions.json

The JSON file contains a list of customer transactions with the following schema:

```json
{
  "transactions": [
    {
      "date": "2024-01-15",
      "transaction_amount": 45.23,
      "customer_id": "1"
    }
  ]
}
```

**Field Descriptions:**

| Field | Type | Description |
|-------|------|-------------|
| `date` | String (YYYY-MM-DD) | Transaction date |
| `transaction_amount` | Number | Transaction amount in dollars (0-150) |
| `customer_id` | String | Customer identifier ("1"-"10") |

**Sample Data:**
- 100 transactions spread over a 2-year period (2024-2026)
- 10 unique customers
- Transaction amounts range from $0 to $150

**Sample Data 2:**
- 300 transactions spread over a 2-year period (2024-2026)
- 2 unique customers
- Transaction amounts range from $0 to $150

## REST API Endpoints

### 1. Get Customer Transactions

Retrieve all transactions for a specific customer in a given quarter.

**Endpoint:**
```
GET /transactions/{customer_id}?year={year}&quarter={quarter}
```

**Parameters:**
- `customer_id` (path) - Customer ID (e.g., "1", "2", "10")
- `year` (query) - Year (e.g., 2024, 2025)
- `quarter` (query) - Quarter (1-4)
  - Q1: January - March
  - Q2: April - June
  - Q3: July - September
  - Q4: October - December

**Example Request:**
```
GET /transactions/1?year=2024&quarter=1
```

**Example Response:**
```json
[
  {
    "date": "2024-01-15",
    "transactionAmount": 45.23,
    "customerId": "1"
  },
  {
    "date": "2024-02-20",
    "transactionAmount": 134.90,
    "customerId": "1"
  }
]
```

### 2. Get Customer Rewards

Calculate total rewards points for a specific customer in a given quarter.

**Endpoint:**
```
GET /rewards/{customer_id}?year={year}&quarter={quarter}
```

**Parameters:**
- `customer_id` (path) - Customer ID (e.g., "1", "2", "10")
- `year` (query) - Year (e.g., 2024, 2025)
- `quarter` (query) - Quarter (1-4)

**Example Request:**
```
GET /rewards/1?year=2024&quarter=1
```

**Example Response:**
```json
  {
    "month": "JULY",
    "reward": 18
  },
  {
    "month": "AUGUST",
    "reward": 587
  },
  {
    "month": "SEPTEMBER",
    "reward": 319
  }
```

## Setup and Installation

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Steps

1. Clone the repository
```bash
git clone <repository-url>
cd rewards-calculator
```

2. Place the `customer_transactions.json` file in `src/main/resources/`

3. Build the project
```bash
mvn clean install
```

4. Run the application
```bash
mvn spring-boot:run
```

## Usage Examples

### Get Q1 2024 transactions for customer "1"
```bash
curl http://localhost:8080/transactions/1?year=2024&quarter=1
```

### Get Q2 2025 rewards for customer "5"
```bash
curl http://localhost:8080/rewards/5?year=2025&quarter=2
```

## License

This is a toy project for educational purposes.
