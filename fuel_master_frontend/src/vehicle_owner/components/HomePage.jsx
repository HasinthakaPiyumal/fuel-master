

function Content() { 
    return (
        <div className="homepage">
          <header className="homepage-header">
            <img src="fuel-logo.svg" alt="Fuel Master Logo" className="logo" />
            <h1>Welcome to the Fuel Master!</h1>
          </header>
          <p className="homepage-subtitle">
            Manage your fuel quota seamlessly by registering your vehicle today!
          </p>
          <ol className="features-list">
            <li>Create a new account or login to an existing account.</li>
            <li>Fill in your vehicle details to register.</li>
            <li>Check the status of your registration anytime.</li>
            <li>Receive your unique QR code for fuel quota management.</li>
          </ol>
          <div className="button-group">
            <button className="login-button">Login</button>
            <button className="create-account-button">Create Account</button>
          </div>
          <img
            src="fuel-station-illustration.png"
            alt="Fuel Station"
            className="homepage-image"
          />
        </div>
      );
}

export default Content;