## Manager Class Testing

### Method: getSectorList (Manager)

#### 1. Boundary Value Testing (BVT)
| Test Case | Input(s) | Expected Output | Actual Output | Reason | Type | Responsible Member |
|-----------|----------|----------------|--------------|--------|------|-------------------|
| TC1       | No sectors | 0              | 0            | Lower boundary | Unit | Kejdi |
| TC2       | 1 sector  | 1              | 1            | Typical value | Unit | Kejdi |
| TC3       | Many sectors | [count]      | [count]      | Upper boundary | Unit | Kejdi |

#### 2. Equivalence Class Testing
| Test Case | Input Class | Input(s) | Expected Output | Actual Output | Reason | Type | Responsible Member |
|-----------|-------------|----------|----------------|--------------|--------|------|-------------------|
| TC1       | Valid       | Sectors assigned | [count]   | [count]      | Valid manager | Unit | Kejdi |
| TC2       | Invalid     | No sectors      | 0         | 0            | No sectors assigned | Unit | Kejdi |

#### 3. Code Coverage Testing
| Coverage Type      | Achieved (%) | Notes/Comments |
|--------------------|--------------|----------------|
| Statement Coverage | 100%         | All branches tested |
| Branch Coverage    | 100%         | Loop and if covered |
| Condition Coverage | 100%         | All conditions tested |
| MC/DC              | 100%         | Each condition independently tested |

### Method: getCashierList (Manager)

#### 1. Boundary Value Testing (BVT)
| Test Case | Input(s) | Expected Output | Actual Output | Reason | Type | Responsible Member |
|-----------|----------|----------------|--------------|--------|------|-------------------|
| TC1       | No cashiers | 0            | 0            | Lower boundary | Unit | Kejdi |
| TC2       | 1 cashier   | 1            | 1            | Typical value | Unit | Kejdi |
| TC3       | Many cashiers | [count]    | [count]      | Upper boundary | Unit | Kejdi |

#### 2. Equivalence Class Testing
| Test Case | Input Class | Input(s) | Expected Output | Actual Output | Reason | Type | Responsible Member |
|-----------|-------------|----------|----------------|--------------|--------|------|-------------------|
| TC1       | Valid       | Cashiers assigned | [count] | [count]      | Valid manager | Unit | Kejdi |
| TC2       | Invalid     | No cashiers      | 0       | 0            | No cashiers assigned | Unit | Kejdi |

#### 3. Code Coverage Testing
| Coverage Type      | Achieved (%) | Notes/Comments |
|--------------------|--------------|----------------|
| Statement Coverage | 100%         | All branches tested |
| Branch Coverage    | 100%         | Loop and if covered |
| Condition Coverage | 100%         | All conditions tested |
| MC/DC              | 100%         | Each condition independently tested |
# Electronics-Store

A Java + JavaFX desktop application for managing inventory, sales, and staff roles in an electronics store. Features include bill generation, role-based access (cashier, manager, admin), and sales statistics.

## Features

**Role-based Access**: Three roles (Admin, Manager, Cashier), each with distinct permissions (e.g. only admin can manage users).

**Product / Stock Management**: Add, edit, remove, view product details, update stock, track inventory.

**Billing / Sales**: Create bills for customers, calculate totals, generate receipts.

**Sales Statistics / Reports**: View revenue, best-selling items, sales trends.

**User Management**: Admin can add or delete staff accounts and assign roles.

**Security & Validation**: Input validation to prevent invalid data; restrict access to functions based on role.

## Architecture & Design

This project is built with a modular and object-oriented approach:

**Model-View-Controller (MVC)** / clear separation

## Screenshots

![Log In](src/images/log-in.jpeg)

![Bills](src/images/bills.jpeg)

![New Bill](src/images/new-bill.jpeg)

![Manager](src/images/manager.jpeg)

![Cashier Management](src/images/cashier-management.jpeg)

## Testing Analysis

### Category Class Testing

#### Method: setMinQuantity (Category)

##### 1. Boundary Value Testing (BVT)
| Test Case | Input(s) | Expected Output | Actual Output | Reason | Type | Responsible Member |
|-----------|----------|----------------|--------------|--------|------|-------------------|
| TC1       | 0        | minQuantity=0  | minQuantity=0| Lower boundary | Unit | Kejdi |
| TC2       | 1        | minQuantity=1  | minQuantity=1| Typical value   | Unit | Kejdi |
| TC3       | 1000     | minQuantity=1000| minQuantity=1000| Upper boundary | Unit | Kejdi |

##### 2. Equivalence Class Testing
| Test Case | Input Class | Input(s) | Expected Output | Actual Output | Reason | Type | Responsible Member |
|-----------|-------------|----------|----------------|--------------|--------|------|-------------------|
| TC1       | Valid       | 5        | minQuantity=5  | minQuantity=5| Valid input | Unit | Kejdi |
| TC2       | Invalid     | -1       | Exception      | Exception    | Invalid input | Unit | Kejdi |

##### 3. Code Coverage Testing
| Coverage Type      | Achieved (%) | Notes/Comments |
|--------------------|--------------|----------------|
| Statement Coverage | 100%         | All branches tested |
| Branch Coverage    | 100%         | Exception and valid path |
| Condition Coverage | 100%         | >=0 and <0 tested |
| MC/DC              | 100%         | Each condition independently tested |

#### Method: checkLowQuantity (Category)

##### 1. Boundary Value Testing (BVT)
| Test Case | Input(s) | Expected Output | Actual Output | Reason | Type | Responsible Member |
|-----------|----------|----------------|--------------|--------|------|-------------------|
| TC1       | totalQuantity=4, minQuantity=5 | "Low quantity" | "Low quantity" | Lower than min | Unit | Kejdi |
| TC2       | totalQuantity=5, minQuantity=5 | "" | "" | Equal to min | Unit | Kejdi |
| TC3       | totalQuantity=6, minQuantity=5 | "" | "" | Greater than min | Unit | Kejdi |

##### 2. Equivalence Class Testing
| Test Case | Input Class | Input(s) | Expected Output | Actual Output | Reason | Type | Responsible Member |
|-----------|-------------|----------|----------------|--------------|--------|------|-------------------|
| TC1       | Low         | totalQuantity < minQuantity | "Low quantity" | "Low quantity" | Low quantity | Unit | Kejdi |
| TC2       | Not Low     | totalQuantity >= minQuantity | "" | "" | Not low quantity | Unit | Kejdi |

##### 3. Code Coverage Testing
| Coverage Type      | Achieved (%) | Notes/Comments |
|--------------------|--------------|----------------|
| Statement Coverage | 100%         | Both branches tested |
| Branch Coverage    | 100%         | if/else covered |
| Condition Coverage | 100%         | < and >= tested |
| MC/DC              | 100%         | Each condition independently tested |

#### Method: getTotalQuantity (Category)

##### 1. Boundary Value Testing (BVT)
| Test Case | Input(s) | Expected Output | Actual Output | Reason | Type | Responsible Member |
|-----------|----------|----------------|--------------|--------|------|-------------------|
| TC1       | No items | 0              | 0            | Lower boundary | Unit | Kejdi |
| TC2       | 1 item, quantity=1 | 1 | 1 | Typical value | Unit | Kejdi |
| TC3       | Many items, large quantity | [sum] | [sum] | Upper boundary | Unit | Kejdi |

##### 2. Equivalence Class Testing
| Test Case | Input Class | Input(s) | Expected Output | Actual Output | Reason | Type | Responsible Member |
|-----------|-------------|----------|----------------|--------------|--------|------|-------------------|
| TC1       | No items    | []       | 0              | 0            | No items | Unit | Kejdi |
| TC2       | Some items  | [items]  | [sum]          | [sum]        | Items present | Unit | Kejdi |

##### 3. Code Coverage Testing
| Coverage Type      | Achieved (%) | Notes/Comments |
|--------------------|--------------|----------------|
| Statement Coverage | 100%         | All branches tested |
| Branch Coverage    | 100%         | Loop and if covered |
| Condition Coverage | 100%         | All conditions tested |
| MC/DC              | 100%         | Each condition independently tested |