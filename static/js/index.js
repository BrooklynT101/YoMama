/* global Vue, axios */

// create the Vue controller
const app = Vue.createApp({
    data() {
        return {
            jokes: [], //hold jokes from the JSON
            joke: "", //current joke displayed
            previousJoke: "" // to keep track of the previous joke
        };
    },
    methods: {
        // Fetch jokes from the JSON file
        async fetchJokes() {
            try {
                const response = await axios.get('./jokes.json');
                this.jokes = response.data.jokes; // Store jokes in array
            } catch (error) {
                console.error("Error fetching jokes:", error);
            }
        },
        
        // Get a new joke ensuring it's not the same as the current one
        getNewJoke() {
            if (this.jokes.length === 0) return; // No jokes available

            let newJoke;
            do {
                const randomIndex = Math.floor(Math.random() * this.jokes.length);
                newJoke = this.jokes[randomIndex];
            } while (newJoke === this.joke); // Ensure new joke is different

            this.previousJoke = this.joke;   // Store current joke as previous
            this.joke = "";                  // Clear for animation reset
            setTimeout(() => {
                this.joke = newJoke;         // Set the new joke after a delay
            }, 100); // Delay to reset the animation
        }
    },
    
    // Automatically fetch jokes when the app is mounted
    mounted() {
        this.fetchJokes();
    }
});

app.mount("main");
