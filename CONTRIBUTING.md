# 🤝 Contributing to AI Chat Assistant

Welcome to the AI Chat Assistant project! We appreciate your interest in contributing. This document provides guidelines and best practices for contributing to this project.

---

## 📌 Introduction

Thank you for considering contributing to the AI Chat Assistant project! We welcome contributions from developers of all skill levels. Whether you're fixing a bug, adding a new feature, improving documentation, or suggesting ideas, your contributions help make this project better for everyone.

By participating in this project, you agree to abide by our code of conduct and contribution guidelines.

---

## 🚀 How to Contribute

### Workflow

1. **Fork the Repository**
   - Click the "Fork" button on the top right of the repository page
   - This creates a personal copy of the repository under your GitHub account

2. **Clone Your Fork**
   ```bash
   git clone https://github.com/YOUR-USERNAME/SpringAIDemo.git
   cd SpringAIDemo
   ```

3. **Create a Feature Branch**
   ```bash
   git checkout -b feature/your-feature-name
   # or
   git checkout -b fix/bug-description
   ```

4. **Make Your Changes**
   - Implement your feature or bug fix
   - Follow the code style guidelines
   - Write tests for new functionality

5. **Commit Your Changes**
   ```bash
   git add .
   git commit -m "feat: add new feature description"
   ```

6. **Push to Your Fork**
   ```bash
   git push origin feature/your-feature-name
   ```

7. **Create a Pull Request**
   - Navigate to the original repository
   - Click "New Pull Request"
   - Fill out the PR template
   - Submit your pull request

---

## 🛠️ Development Setup

### Prerequisites

| Tool | Version |
|------|---------|
| Java | 17+ |
| Node.js | 18+ |
| Maven | 3.8+ |
| npm | 9+ |

### Backend Setup

1. Navigate to the SpringAIDemo directory:
   ```bash
   cd SpringAIDemo
   ```

2. Copy the example configuration file:
   ```bash
   cp src/main/resources/application-local.properties.example src/main/resources/application-local.properties
   ```

3. Add your Google Gemini API key to `application-local.properties`:
   ```properties
   spring.ai.gemini.api-key=YOUR_API_KEY_HERE
   ```

4. Build the project:
   ```bash
   ./mvnw clean install
   ```

5. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

### Docker Setup (Optional)

For containerized development:

```bash
docker-compose up --build
```

---

## 📝 Code Style Guidelines

### Java/Spring Boot

- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Use meaningful variable and method names
- Add JavaDoc comments for public APIs
- Keep methods focused and concise (ideally under 30 lines)
- Use Lombok annotations to reduce boilerplate
- Follow Spring Boot best practices

### React/JavaScript

- Use functional components with hooks
- Follow [Airbnb JavaScript Style Guide](https://github.com/airbnb/javascript)
- Use meaningful component and variable names
- Keep components small and focused
- Use proper prop types and TypeScript when possible

### General Guidelines

- Use 2 spaces for indentation
- Maximum line length: 100 characters
- Remove console.log statements and debug code before committing
- Add comments for complex logic only

---

## 🧪 Testing Requirements

### Backend Tests

All new features must include appropriate unit tests. Run tests with:

```bash
./mvnw test
```

### Test Coverage Requirements

- Service layer: Minimum 80% coverage
- Controller layer: All endpoints should have integration tests
- Exception handling: Test all error scenarios

### Frontend Tests

Run tests with:

```bash
cd frontend
npm run test
```

### Testing Best Practices

- Write descriptive test names that explain what is being tested
- Use Given-When-Then (GWT) format for test descriptions
- Mock external dependencies (API calls, services)
- Test both success and error scenarios
- Ensure tests are independent and can run in any order

---

## 📏 Commit Message Guidelines

### Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types

| Type | Description |
|------|-------------|
| `feat` | New feature |
| `fix` | Bug fix |
| `docs` | Documentation changes |
| `style` | Code style changes (formatting, semicolons) |
| `refactor` | Code refactoring |
| `test` | Adding or updating tests |
| `chore` | Maintenance tasks |

### Examples

```
feat(chat): add session-based conversation history

- Implemented in-memory session storage
- Added session ID generation
- Updated chat service to track context

Closes #123
```

```
fix(api): handle Gemini API timeout errors

- Added retry logic with exponential backoff
- Improved error messaging for users

Fixes #456
```

### Rules

- Use imperative mood (add, not added)
- Subject line: max 50 characters
- Body: wrap at 72 characters
- Reference issues in footer when applicable

---

## 📋 Pull Request Guidelines

### Before Submitting

- [ ] Code follows code style guidelines
- [ ] Tests pass locally (`./mvnw test` and `npm run test`)
- [ ] New code includes appropriate tests
- [ ] Documentation is updated if needed
- [ ] Commit messages are clear and descriptive

### PR Template

```markdown
## Description
Brief description of the changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Documentation update
- [ ] Refactoring

## Testing
Describe testing performed

## Checklist
- [ ] My code follows the style guidelines
- [ ] I have performed self-review
- [ ] I have commented my code where necessary
- [ ] I have updated the documentation
- [ ] My changes generate no new warnings
- [ ] I have added tests that prove my fix is effective
- [ ] New and existing unit tests pass locally
```

### Review Process

1. Maintainers will review your PR within 3-5 days
2. Address any feedback promptly
3. Once approved, your PR will be merged
4. Thank you for your contribution!

---

## 🐛 Issue Reporting Guidelines

### Before Reporting

- Search existing issues to avoid duplicates
- Check if the issue has been fixed in the latest version

### How to Report

When creating an issue, include:

1. **Title**: Clear, descriptive title
2. **Description**: Detailed explanation of the problem
3. **Steps to Reproduce**: Numbered list of steps
4. **Expected Behavior**: What should happen
5. **Actual Behavior**: What actually happens
6. **Environment**: OS, Java version, Node version, etc.
7. **Screenshots**: If applicable

### Issue Templates

Use the appropriate template for:
- Bug reports
- Feature requests
- Questions

---

## 📄 License

By contributing to the AI Chat Assistant project, you agree that your contributions will be licensed under the [MIT License](LICENSE).

---

## 💬 Getting Help

- **Questions**: Open a discussion on GitHub
- **Issues**: Use the issue tracker for bugs and feature requests
- **Discord**: Join our community Discord server (link in README)

---

Thank you for contributing! 🎉
